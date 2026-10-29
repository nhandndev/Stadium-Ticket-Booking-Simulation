# -*- coding: utf-8 -*-
import os
import random
from datetime import datetime, timedelta

random.seed(42)

ho = ['Nguyen', 'Tran', 'Le', 'Pham', 'Hoang', 'Phan', 'Vu', 'Dang', 'Bui', 'Do', 'Ho', 'Ngo', 'Duong', 'Ly']
dem = ['Van', 'Thi', 'Duc', 'Ngoc', 'Minh', 'Quang', 'Huu', 'Thanh', 'Tuan', 'Gia', 'Hai', 'Xuan', 'Dinh']
ten = ['An', 'Binh', 'Cuong', 'Dung', 'Em', 'Phuc', 'Giang', 'Huy', 'Khoa', 'Long', 'Nam', 'Phong', 'Quan', 'Son', 'Thang', 'Tung', 'Viet', 'Yen', 'Linh', 'Trang', 'Mai', 'Huong']

provinces = ['Hanoi', 'HCMC', 'Da Nang', 'Hai Phong', 'Can Tho', 'Binh Duong', 'Dong Nai', 'Quang Ninh', 'Nghe An', 'Thanh Hoa', 'Khanh Hoa', 'Lam Dong', 'Hue', 'Nam Dinh']
streets = ['Nguyen Trai', 'Le Loi', 'Tran Hung Dao', 'Nguyen Hue', 'Hai Ba Trung', 'Vo Van Kiet', 'Pham Van Dong', 'Le Duan', 'Dien Bien Phu']

def get_name():
    return f"{random.choice(ho)} {random.choice(dem)} {random.choice(ten)}"

print("1. Generating stadiums.csv (10,000 records)...")
with open('data/stadiums.csv', 'w', encoding='utf-8') as f:
    f.write('1,My Dinh National Stadium,Le Duc Tho - Hanoi
')
    f.write('2,Hang Day Stadium,Trinh Hoai Duc - Hanoi
')
    for i in range(3, 10001):
        city = random.choice(provinces)
        street = random.choice(streets)
        sname = f"{city} Arena #{i}"
        saddr = f"{random.randint(10, 999)} {street} - {city}"
        f.write(f"{i},{sname},{saddr}
")

print("2. Generating sections.csv (10,000 records)...")
section_types = ['Stand A VIP', 'Stand B', 'Stand C', 'Stand D', 'East Stand', 'West Stand', 'North Stand', 'South Stand', 'Club Level', 'Executive Box']
with open('data/sections.csv', 'w', encoding='utf-8') as f:
    f.write('1,1,Stand A VIP
')
    f.write('2,1,Stand B
')
    f.write('3,1,Stand C
')
    f.write('4,1,Stand D
')
    f.write('5,2,Main Stand
')
    for i in range(6, 10001):
        stadium_id = ((i - 1) % 1000) + 1
        stype = random.choice(section_types)
        sec_name = f"{stype} (Sec {i})"
        f.write(f"{i},{stadium_id},{sec_name}
")

print("3. Generating seats.csv (10,000 records)...")
rows = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K']
with open('data/seats.csv', 'w', encoding='utf-8') as f:
    for i in range(1, 21):
        sec_id = 1 if i <= 10 else 2
        f.write(f"{i},{sec_id},A,{i},true
")
    for i in range(21, 10001):
        sec_id = ((i - 1) % 1000) + 1
        row = rows[(i // 50) % len(rows)]
        seat_num = (i % 50) + 1
        active = "true" if random.random() > 0.01 else "false"
        f.write(f"{i},{sec_id},{row},{seat_num},{active}
")

print("4. Generating matches.csv (10,000 records)...")
teams = ['Vietnam', 'Thailand', 'Indonesia', 'Malaysia', 'Singapore', 'Japan', 'South Korea', 'Australia', 'Hanoi FC', 'Hai Phong FC', 'HAGL', 'Viettel FC', 'SLNA', 'Nam Dinh FC', 'Binh Duong FC', 'Da Nang FC']
base_time = datetime(2026, 11, 15, 19, 0, 0)
with open('data/matches.csv', 'w', encoding='utf-8') as f:
    f.write('1,1,Vietnam,Thailand,2026-11-15T19:00,OPEN
')
    f.write('2,1,Vietnam,Indonesia,2026-12-01T19:30,OPEN
')
    f.write('3,2,Hanoi FC,Hai Phong FC,2026-11-20T18:00,CLOSED
')
    for i in range(4, 10001):
        stadium_id = ((i - 1) % 1000) + 1
        t1, t2 = random.sample(teams, 2)
        match_dt = base_time + timedelta(hours=i * 4)
        dt_str = match_dt.strftime('%Y-%m-%dT%H:%M')
        sale_status = random.choices(['OPEN', 'CLOSED', 'PAUSED'], weights=[70, 20, 10])[0]
        f.write(f"{i},{stadium_id},{t1},{t2},{dt_str},{sale_status}
")

print("5. Generating ticket_prices.csv (10,000 records)...")
prices = [100000, 150000, 200000, 250000, 300000, 400000, 500000, 600000, 750000, 1000000]
with open('data/ticket_prices.csv', 'w', encoding='utf-8') as f:
    f.write('1,1,500000
')
    f.write('1,2,300000
')
    f.write('1,3,200000
')
    f.write('1,4,150000
')
    f.write('2,1,600000
')
    f.write('2,2,400000
')
    count = 6
    # generate unique (matchId, sectionId)
    # 2500 matches x 4 sections = 10,000 lines
    for m in range(1, 2501):
        for s in range(1, 5):
            if (m == 1 and s <= 4) or (m == 2 and s <= 2):
                continue
            amt = prices[(m + s) % len(prices)]
            f.write(f"{m},{s},{amt}
")
            count += 1
            if count >= 10000:
                break
        if count >= 10000:
            break

print("6. Generating match_seats.csv (10,000 records)...")
with open('data/match_seats.csv', 'w', encoding='utf-8') as f:
    for i in range(1, 21):
        f.write(f"{i},1,{i},AVAILABLE,0
")
    for i in range(21, 10001):
        # distributed across matches 1..10
        m_id = ((i - 1) % 10) + 1
        seat_id = i
        status = random.choices(['AVAILABLE', 'BOOKED', 'RESERVED'], weights=[85, 12, 3])[0]
        ver = 0
        f.write(f"{i},{m_id},{seat_id},{status},{ver}
")

print("7. Generating fans.csv (10,000 records)...")
with open('data/fans.csv', 'w', encoding='utf-8') as f:
    f.write('1,Nguyen Van An,an.nguyen@gmail.com,123456,0912345601,FAN,ACTIVE
')
    f.write('2,Tran Thi Binh,binh.tran@gmail.com,123456,0912345602,FAN,ACTIVE
')
    f.write('3,Le Hoang Cuong,cuong.le@gmail.com,123456,0912345603,FAN,ACTIVE
')
    f.write('4,Pham Minh Dung,dung.pham@gmail.com,123456,0912345604,FAN,ACTIVE
')
    f.write('5,Do Thi Em,em.do@gmail.com,123456,0912345605,FAN,INACTIVE
')
    for i in range(6, 10001):
        name = get_name()
        email = f"fan{i}@gmail.com"
        phone = f"09{i:08d}"
        status = "ACTIVE" if random.random() > 0.05 else "INACTIVE"
        f.write(f"{i},{name},{email},123456,{phone},FAN,{status}
")

print("8. Generating staff.csv (10,000 records)...")
with open('data/staff.csv', 'w', encoding='utf-8') as f:
    f.write('1,Administrator,admin@stadium.com,admin123,0900000001,ADMIN,ACTIVE
')
    f.write('2,Seller Nguyen,seller1@stadium.com,seller123,0900000002,SELLER,ACTIVE
')
    f.write('3,Seller Tran,seller2@stadium.com,seller123,0900000003,SELLER,ACTIVE
')
    f.write('4,Gate Staff Le,gate1@stadium.com,gate123,0900000004,GATE_STAFF,ACTIVE
')
    f.write('5,Simulator Operator,sim@stadium.com,sim123,0900000005,SIMULATOR_OPERATOR,ACTIVE
')
    roles = ['SELLER', 'GATE_STAFF', 'SIMULATOR_OPERATOR', 'ADMIN']
    weights = [45, 45, 8, 2]
    for i in range(6, 10001):
        name = get_name()
        email = f"staff{i}@stadium.com"
        phone = f"08{i:08d}"
        role = random.choices(roles, weights=weights)[0]
        status = "ACTIVE" if random.random() > 0.02 else "INACTIVE"
        f.write(f"{i},{name},{email},admin123,{phone},{role},{status}
")

print("9. Generating audit_logs.csv (10,000 records)...")
actions = ['LOGIN', 'LOGOUT', 'SEARCH_MATCHES', 'VIEW_SEAT_MAP', 'OPEN_TICKET_SALES', 'CLOSE_TICKET_SALES', 'ASSIGN_ROLE', 'SYSTEM_BACKUP']
targets = ['USER', 'MATCH', 'SEAT', 'STADIUM', 'SYSTEM']
base_audit = datetime(2026, 10, 26, 8, 0, 0)
with open('data/audit_logs.csv', 'w', encoding='utf-8') as f:
    f.write('1,1,INITIALIZE_DATASET,SYSTEM,0,SUCCESS,2026-10-26T00:00:00
')
    f.write('2,1,OPEN_TICKET_SALES,MATCH,1,SUCCESS,2026-10-26T00:05:00
')
    f.write('3,1,ASSIGN_ROLE,STAFF,3,SUCCESS,2026-10-26T00:10:00
')
    for i in range(4, 10001):
        actor_id = random.randint(1, 50)
        action = random.choice(actions)
        target = random.choice(targets)
        target_id = random.randint(1, 1000)
        result = "SUCCESS" if random.random() > 0.05 else "FAILED"
        dt = base_audit + timedelta(seconds=i * 12)
        dt_str = dt.strftime('%Y-%m-%dT%H:%M:%S')
        f.write(f"{i},{actor_id},{action},{target},{target_id},{result},{dt_str}
")

print("All 9 CSV files generated with exactly 10,000 rows each!")
