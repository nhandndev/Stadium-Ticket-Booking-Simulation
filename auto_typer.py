#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Script giả lập gõ code tự động trên IntelliJ IDEA (macOS)
Dành cho đề tài Stadium Ticket Booking Simulation - Stage 07 (Admin)
Mô phỏng 100% phong cách lập trình viên thực thụ:
- Chèn nhanh khối import (không ai ngồi gõ tay 15 dòng import)
- Bắt đầu gõ từ public class ...
- Giả lập bấm phím TAB ăn gợi ý cho các từ dài (StadiumRepository, AppException,...)
- Tự động cuộn màn hình xuống dưới cùng liên tục trong khi code (Auto-scroll to bottom)
- Ngắt nghỉ tự nhiên theo từ
"""

import os
import sys
import time
import random
import re
import subprocess
from pathlib import Path

# --- macOS CoreGraphics API qua ctypes (Native macOS C framework) ---
import ctypes
import ctypes.util

try:
    app_services = ctypes.cdll.LoadLibrary(ctypes.util.find_library("ApplicationServices"))
    
    CGEventCreateKeyboardEvent = app_services.CGEventCreateKeyboardEvent
    CGEventCreateKeyboardEvent.argtypes = [ctypes.c_void_p, ctypes.c_ushort, ctypes.c_bool]
    CGEventCreateKeyboardEvent.restype = ctypes.c_void_p

    CGEventSetFlags = app_services.CGEventSetFlags
    CGEventSetFlags.argtypes = [ctypes.c_void_p, ctypes.c_uint64]

    CGEventKeyboardSetUnicodeString = app_services.CGEventKeyboardSetUnicodeString
    CGEventKeyboardSetUnicodeString.argtypes = [ctypes.c_void_p, ctypes.c_ulong, ctypes.c_wchar_p]

    CGEventCreateScrollWheelEvent = app_services.CGEventCreateScrollWheelEvent
    CGEventCreateScrollWheelEvent.argtypes = [ctypes.c_void_p, ctypes.c_uint32, ctypes.c_uint32, ctypes.c_int32]
    CGEventCreateScrollWheelEvent.restype = ctypes.c_void_p

    CGEventPost = app_services.CGEventPost
    CGEventPost.argtypes = [ctypes.c_uint32, ctypes.c_void_p]

    CFRelease = app_services.CFRelease
    CFRelease.argtypes = [ctypes.c_void_p]
    HAS_CORE_GRAPHICS = True
except Exception:
    HAS_CORE_GRAPHICS = False

# macOS Virtual Keycodes (Carbon HIToolbox Events.h)
kVK_Return = 36          # 0x24 - Phím Enter / Return
kVK_Tab = 48             # 0x30 - Phím Tab
kVK_Delete = 51          # 0x33 - Phím Backspace / Delete
kVK_DownArrow = 125      # 0x7D - Phím Mũi tên xuống
kVK_ANSI_A = 0           # 0x00 - Phím 'A'
kVK_ANSI_V = 9           # 0x09 - Phím 'V'


def activate_intellij():
    """Kích hoạt và đưa cửa sổ IntelliJ IDEA lên trước"""
    script = 'tell application "IntelliJ IDEA" to activate'
    subprocess.run(["osascript", "-e", script], capture_output=True)


def open_file_in_intellij(file_path):
    """Mở file trực tiếp trong IntelliJ IDEA"""
    abs_path = str(Path(file_path).resolve())
    p = Path(abs_path)
    if not p.exists():
        p.parent.mkdir(parents=True, exist_ok=True)
        p.write_text("", encoding="utf-8")
    subprocess.run(["open", "-a", "IntelliJ IDEA", abs_path], capture_output=True)


def smooth_scroll_down(ticks=3):
    """
    Cuộn màn hình xuống dưới cùng (hành vi người code kéo màn hình xuống để vùng gõ luôn thấy rõ)
    """
    if HAS_CORE_GRAPHICS:
        try:
            # 1 = kCGScrollEventUnitLine, -ticks = cuộn xuống dưới
            ev = CGEventCreateScrollWheelEvent(None, 1, 1, -ticks)
            CGEventPost(0, ev)
            CFRelease(ev)
        except Exception:
            pass
    else:
        script = 'tell application "System Events" to key code 125 using control down'
        subprocess.run(["osascript", "-e", script], capture_output=True)


def press_return():
    """Phát sự kiện phím Return / Enter thực sự (Keycode 36)"""
    if HAS_CORE_GRAPHICS:
        ev_down = CGEventCreateKeyboardEvent(None, kVK_Return, True)
        CGEventSetFlags(ev_down, 0)
        CGEventPost(0, ev_down)
        CFRelease(ev_down)
        time.sleep(0.015)
        ev_up = CGEventCreateKeyboardEvent(None, kVK_Return, False)
        CGEventSetFlags(ev_up, 0)
        CGEventPost(0, ev_up)
        CFRelease(ev_up)
    else:
        script = 'tell application "System Events" to key code 36'
        subprocess.run(["osascript", "-e", script], capture_output=True)


def select_all_and_delete():
    """Nhấn Cmd + A và Delete sạch sẽ bằng AppleScript"""
    script = """
    tell application "System Events"
        keystroke "a" using command down
        delay 0.1
        key code 51
    end tell
    """
    subprocess.run(["osascript", "-e", script], capture_output=True)
    time.sleep(0.4)


def type_unicode_char(char):
    """Gõ 1 ký tự vào cửa sổ đang active (Đảm bảo Flags = 0)"""
    if char == "\n" or char == "\r":
        press_return()
        return

    if not HAS_CORE_GRAPHICS:
        if char == '"':
            cmd = 'tell application "System Events" to keystroke "\\""'
        elif char == '\\':
            cmd = 'tell application "System Events" to keystroke "\\\\"'
        else:
            cmd = f'tell application "System Events" to keystroke "{char}"'
        subprocess.run(["osascript", "-e", cmd], capture_output=True)
        return

    ev_down = CGEventCreateKeyboardEvent(None, 0, True)
    CGEventSetFlags(ev_down, 0)
    CGEventKeyboardSetUnicodeString(ev_down, 1, char)
    CGEventPost(0, ev_down)
    CFRelease(ev_down)

    ev_up = CGEventCreateKeyboardEvent(None, 0, False)
    CGEventSetFlags(ev_up, 0)
    CGEventKeyboardSetUnicodeString(ev_up, 1, char)
    CGEventPost(0, ev_up)
    CFRelease(ev_up)


def paste_string(text):
    """Dán text bằng Clipboard (pbcopy + Cmd+V sạch sẽ)"""
    p = subprocess.Popen(["pbcopy"], stdin=subprocess.PIPE)
    p.communicate(text.encode("utf-8"))
    time.sleep(0.02)

    script = """
    tell application "System Events"
        keystroke "v" using command down
    end tell
    """
    subprocess.run(["osascript", "-e", script], capture_output=True)
    time.sleep(0.02)


def get_speed_config(speed_level):
    """Cấu hình tốc độ gõ phím"""
    if speed_level == 1:
        # 1. Siêu chậm (~35-45 WPM - Rất chậm rãi, ngắt nghỉ từng nhịp)
        return {
            "char_min": 0.045, "char_max": 0.080,
            "word_pause_min": 0.20, "word_pause_max": 0.40,
            "space_pause_min": 0.12, "space_pause_max": 0.25,
            "punct_pause_min": 0.45, "punct_pause_max": 0.85,
            "minor_punct_min": 0.15, "minor_punct_max": 0.30,
            "eol_pause_min": 0.40, "eol_pause_max": 0.70,
            "newline_pause_min": 0.30, "newline_pause_max": 0.55,
            "empty_line_delay": 0.35,
            "tab_pause_min": 0.15, "tab_pause_max": 0.30,
        }
    elif speed_level == 2:
        # 2. Chậm vừa / Tự nhiên (~60-70 WPM - Khuyên dùng quay video)
        return {
            "char_min": 0.020, "char_max": 0.045,
            "word_pause_min": 0.10, "word_pause_max": 0.22,
            "space_pause_min": 0.06, "space_pause_max": 0.14,
            "punct_pause_min": 0.25, "punct_pause_max": 0.50,
            "minor_punct_min": 0.08, "minor_punct_max": 0.18,
            "eol_pause_min": 0.20, "eol_pause_max": 0.40,
            "newline_pause_min": 0.18, "newline_pause_max": 0.35,
            "empty_line_delay": 0.20,
            "tab_pause_min": 0.10, "tab_pause_max": 0.20,
        }
    elif speed_level == 3:
        # 3. Nhanh (~100 WPM)
        return {
            "char_min": 0.010, "char_max": 0.022,
            "word_pause_min": 0.05, "word_pause_max": 0.10,
            "space_pause_min": 0.03, "space_pause_max": 0.06,
            "punct_pause_min": 0.12, "punct_pause_max": 0.25,
            "minor_punct_min": 0.04, "minor_punct_max": 0.08,
            "eol_pause_min": 0.10, "eol_pause_max": 0.20,
            "newline_pause_min": 0.08, "newline_pause_max": 0.16,
            "empty_line_delay": 0.10,
            "tab_pause_min": 0.05, "tab_pause_max": 0.10,
        }
    else:
        # 4. Siêu tốc (Turbo)
        return {
            "char_min": 0.003, "char_max": 0.006,
            "word_pause_min": 0.01, "word_pause_max": 0.02,
            "space_pause_min": 0.01, "space_pause_max": 0.01,
            "punct_pause_min": 0.03, "punct_pause_max": 0.06,
            "minor_punct_min": 0.01, "minor_punct_max": 0.02,
            "eol_pause_min": 0.03, "eol_pause_max": 0.06,
            "newline_pause_min": 0.02, "newline_pause_max": 0.05,
            "empty_line_delay": 0.03,
            "tab_pause_min": 0.02, "tab_pause_max": 0.04,
        }


def type_token_with_tab(token, cfg, simulate_tab=True):
    """
    Gõ 1 từ với hiệu ứng nhấn phím TAB ăn gợi ý của IntelliJ:
    - Nếu là tên class / method / biến dài (> 6 ký tự, VD: StadiumRepository, AppException):
      Gõ 3-4 chữ đầu -> khựng lại nhẹ như nhìn thấy popup gợi ý -> bắn nốt phần đuôi tức thì!
    - Nếu là từ ngắn hoặc keyword thông thường: gõ bình thường.
    """
    keywords = {"public", "private", "protected", "static", "final", "class", "interface",
                "return", "import", "package", "throw", "throws", "new", "null", "true", "false"}

    if simulate_tab and len(token) > 6 and token not in keywords and token.isidentifier():
        if random.random() < 0.85:
            prefix_len = min(random.choice([3, 4]), len(token) - 2)
            prefix = token[:prefix_len]
            suffix = token[prefix_len:]

            # 1. Gõ 3-4 ký tự đầu với tốc độ bình thường
            for ch in prefix:
                type_unicode_char(ch)
                time.sleep(random.uniform(cfg["char_min"], cfg["char_max"]))

            # 2. Khựng lại một nhịp ngắn (như nhìn thấy gợi ý popup)
            time.sleep(random.uniform(cfg["tab_pause_min"], cfg["tab_pause_max"]))

            # 3. Bắn nốt phần đuôi tức thì (như vừa bấm Tab / Enter)
            for ch in suffix:
                type_unicode_char(ch)
                time.sleep(0.003)

            time.sleep(random.uniform(cfg["word_pause_min"], cfg["word_pause_max"]))
            return

    for ch in token:
        type_unicode_char(ch)
        time.sleep(random.uniform(cfg["char_min"], cfg["char_max"]))
    time.sleep(random.uniform(cfg["word_pause_min"], cfg["word_pause_max"]))


def type_human_line(line, cfg, simulate_tab=True):
    """Gõ 1 dòng code theo nhịp người thật"""
    if not line.strip():
        press_return()
        time.sleep(cfg["empty_line_delay"])
        return

    # Tách dòng thành các token
    tokens = re.findall(r"\s+|[a-zA-Z0-9_]+|[^\s\w]", line)

    for token in tokens:
        if token.isspace():
            for ch in token:
                type_unicode_char(ch)
                time.sleep(cfg["char_min"])
            time.sleep(random.uniform(cfg["space_pause_min"], cfg["space_pause_max"]))
        elif token.isalnum() or "_" in token:
            type_token_with_tab(token, cfg, simulate_tab=simulate_tab)
        else:
            for ch in token:
                type_unicode_char(ch)
                delay = random.uniform(cfg["char_min"], cfg["char_max"])
                time.sleep(delay)
            if token in (";", "{", "}"):
                time.sleep(random.uniform(cfg["punct_pause_min"], cfg["punct_pause_max"]))
            elif token in (",", "(", ")"):
                time.sleep(random.uniform(cfg["minor_punct_min"], cfg["minor_punct_max"]))

    # Cuối dòng: Dừng quan sát rồi Enter xuống dòng
    time.sleep(random.uniform(cfg["eol_pause_min"], cfg["eol_pause_max"]))
    press_return()
    time.sleep(random.uniform(cfg["newline_pause_min"], cfg["newline_pause_max"]))


def split_header_and_body(code):
    """
    Tách code thành 2 phần:
    - header: package và các dòng import
    - body: từ public class / interface trở đi
    """
    lines = code.strip().splitlines()
    header_lines = []
    body_lines = []
    found_class = False

    for line in lines:
        if not found_class:
            if (line.strip().startswith("public class ") or
                line.strip().startswith("public interface ") or
                line.strip().startswith("public enum ")):
                found_class = True
                body_lines.append(line)
            else:
                header_lines.append(line)
        else:
            body_lines.append(line)

    return header_lines, body_lines


def load_stage_files(stage_path):
    """Đọc toàn bộ file code từ markdown stage"""
    txt = Path(stage_path).read_text(encoding="utf-8")
    pat = re.compile(r"`(src/[^`]+\.java)`:\n\n```java\n(.*?)\n```", re.DOTALL)
    matches = pat.findall(txt)
    return matches


def print_progress_bar(current, total, prefix="Tiến độ:", length=40):
    percent = float(current) / float(total) if total > 0 else 1.0
    filled = int(length * percent)
    bar = "█" * filled + "░" * (length - filled)
    sys.stdout.write(f"\r{prefix} |{bar}| {int(percent * 100)}% ({current}/{total} dòng)")
    sys.stdout.flush()
    if current == total:
        print()


def run_typing(file_path, code, mode=1, speed=2, clear_first=False):
    """
    Thực thi việc giả lập gõ code vào IntelliJ IDEA
    Có tính năng tự động cuộn màn hình xuống dưới cùng (Auto scroll to bottom)
    """
    cfg = get_speed_config(speed)

    print("\n" + "=" * 65)
    print(f"🚀 CHUẨN BỊ GÕ FILE: {file_path}")
    if mode == 1:
        print("⚙️  Chế độ: [Chuẩn Dev IntelliJ] Chèn sẵn Import -> Gõ Class Logic (Tab AutoComplete)")
    elif mode == 2:
        print("⚙️  Chế độ: [Gõ thủ công] Gõ từng ký tự từ đầu tới cuối cả Import")
    else:
        print("⚙️  Chế độ: [Gõ từng dòng] Line-by-line Smart Flow")
    print("=" * 65)

    print("\n[+] Đang mở file trong IntelliJ IDEA...")
    open_file_in_intellij(file_path)
    time.sleep(1.0)
    activate_intellij()

    print("\n⚠️  LƯU Ý TRƯỚC KHI BẮT ĐẦU:")
    print(" 1. Chuyển bộ gõ sang Tiếng Anh (US/ABC) để không bị dính dấu tiếng Việt!")
    print(" 2. Click chuột vào cửa sổ soạn thảo IntelliJ IDEA (chỗ con trỏ nhấp nháy).")
    print(" 3. Nếu muốn DỪNG KHẨN CẤP, nhấn Ctrl + C trong Terminal này.\n")

    print("Bắt đầu sau:")
    for count in range(5, 0, -1):
        print(f"   ⏳ {count} giây...")
        time.sleep(1.0)
    print(">>> 🟢 BẮT ĐẦU GÕ!\n")

    if clear_first:
        select_all_and_delete()
        time.sleep(0.3)

    try:
        if mode == 1:
            # Chế độ chuẩn Dev: Chèn nhanh package + imports, sau đó gõ class logic
            header_lines, body_lines = split_header_and_body(code)
            
            # 1. Chèn nhanh package + imports
            if header_lines:
                print("⚡ Đang chèn phần package & imports...")
                header_text = "\n".join(header_lines) + "\n\n"
                paste_string(header_text)
                time.sleep(0.5)
                # Cuộn màn hình xuống dưới để tập trung vào phần class chuẩn bị gõ
                smooth_scroll_down(ticks=8)
                time.sleep(0.3)

            # 2. Bắt đầu gõ từng chữ phần class logic (có Tab gợi ý & tự động cuộn màn hình)
            total_lines = len(body_lines)
            print("✍️ Bắt đầu gõ phần Class logic (có ngắt nghỉ, Tab gợi ý & Auto-scroll)...")
            for idx, line in enumerate(body_lines, 1):
                type_human_line(line, cfg, simulate_tab=True)
                
                # Cứ mỗi 2 dòng code hoặc khi gặp dấu đóng/mở khối, tự động cuộn màn hình xuống dưới 2 nấc
                if idx % 2 == 0 or line.strip().endswith("{") or line.strip() == "}":
                    smooth_scroll_down(ticks=2)

                print_progress_bar(idx, total_lines)

            # Cuộn xuống dưới cùng khi kết thúc file
            smooth_scroll_down(ticks=6)

        elif mode == 2:
            # Gõ thủ công toàn bộ từ đầu đến cuối
            lines = code.strip().splitlines()
            total_lines = len(lines)
            for idx, line in enumerate(lines, 1):
                type_human_line(line, cfg, simulate_tab=True)
                if idx % 2 == 0:
                    smooth_scroll_down(ticks=2)
                print_progress_bar(idx, total_lines)
            smooth_scroll_down(ticks=6)

        else:
            # Gõ từng dòng (Line-by-line paste)
            lines = code.strip().splitlines()
            total_lines = len(lines)
            for idx, line in enumerate(lines, 1):
                if line.strip():
                    paste_string(line)
                    time.sleep(0.02)
                press_return()
                time.sleep(cfg["empty_line_delay"])
                if idx % 3 == 0:
                    smooth_scroll_down(ticks=2)
                print_progress_bar(idx, total_lines)
            smooth_scroll_down(ticks=6)

        print("\n\n🎉 HOÀN THÀNH GÕ FILE: " + file_path)
    except KeyboardInterrupt:
        print("\n\n🛑 ĐÃ DỪNG GÕ KHẨN CẤP THEO YÊU CỦA BẠN (Ctrl + C)!")


def main():
    stage_file = "secret/Code/Controller_First/07_ADMIN.md"
    if not os.path.exists(stage_file):
        print(f"Không tìm thấy file: {stage_file}")
        sys.exit(1)

    files = load_stage_files(stage_file)
    if not files:
        print(f"Không tìm thấy code block nào trong {stage_file}")
        sys.exit(1)

    while True:
        print("\n" + "=" * 65)
        print("🎯 TOOL GIẢ LẬP GÕ CODE TỰ ĐỘNG LÊN INTELLIJ IDEA (CHẶNG 07)")
        print("=" * 65)
        for idx, (fpath, code) in enumerate(files, 1):
            line_count = len(code.strip().splitlines())
            name = Path(fpath).name
            print(f" {idx:2d}. {name:<26} ({line_count:3d} dòng) -> {fpath}")
        print("-----------------------------------------------------------------")
        print(" A. [Tất cả] Gõ lần lượt từng file")
        print(" 0. Thoát")
        print("=" * 65)

        choice = input("👉 Nhập số thứ tự file bạn muốn gõ (VD: 1 cho StadiumService): ").strip()
        if choice == "0":
            print("Tạm biệt!")
            break

        selected_files = []
        if choice.upper() == "A":
            selected_files = files
        else:
            try:
                num = int(choice)
                if 1 <= num <= len(files):
                    selected_files = [files[num - 1]]
                else:
                    print("Lựa chọn không hợp lệ!")
                    continue
            except ValueError:
                print("Vui lòng nhập số hợp lệ!")
                continue

        print("\nChọn chế độ gõ:")
        print(" 1. [Chuẩn Dev IntelliJ - KHUYÊN DÙNG]:")
        print("    -> Chèn sẵn Package & Import trong 1s (không ai gõ tay 15 dòng import)")
        print("    -> Gõ từng chữ phần Class logic có ngắt nghỉ & Tab ăn gợi ý")
        print("    -> Tự động cuộn màn hình xuống dưới cùng (Auto-scroll to bottom)")
        print(" 2. Gõ thủ công từ đầu đến cuối (Gõ cả phần import)")
        print(" 3. Gõ từng dòng (Line-by-line Smart Flow)")
        m_choice = input("👉 Chọn chế độ [1, 2, hoặc 3, mặc định 1]: ").strip()
        mode = 2 if m_choice == "2" else (3 if m_choice == "3" else 1)

        print("\nChọn tốc độ gõ:")
        print(" 1. Siêu chậm (~35-45 WPM - Học sinh tập gõ, rất chậm rãi và tự nhiên)")
        print(" 2. Chậm vừa / Tự nhiên (~60-70 WPM - Nhịp gõ người thật, khuyên dùng)")
        print(" 3. Nhanh (~100 WPM - Lập trình viên quen tay)")
        print(" 4. Siêu tốc (Chỉ dùng để test nhanh)")
        s_choice = input("👉 Chọn tốc độ [1, 2, 3, hoặc 4, mặc định 2]: ").strip()
        speed = int(s_choice) if s_choice in ("1", "2", "3", "4") else 2

        c_choice = input("\n👉 Bạn có muốn tự động xóa sạch file trước khi gõ không? [y/N, mặc định N]: ").strip().lower()
        clear_first = True if c_choice == "y" else False

        for fpath, code in selected_files:
            run_typing(fpath, code, mode=mode, speed=speed, clear_first=clear_first)
            if len(selected_files) > 1:
                cont = input("\nNhấn Enter để tiếp tục sang file tiếp theo (hoặc gõ 'q' để dừng): ")
                if cont.strip().lower() == "q":
                    break


if __name__ == "__main__":
    main()
