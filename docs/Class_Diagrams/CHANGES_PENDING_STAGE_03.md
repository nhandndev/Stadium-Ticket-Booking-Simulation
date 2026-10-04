# Class diagram changes to apply after Stage 03

Status: pending. This file records differences between the current Java code and the class diagrams. The `.mmd` diagrams have not been edited yet.

## Changes to draw later

1. In `01_Main_View_Auth.mmd`, add `+setId(id: Long): void` to the existing `BaseEntity` class, immediately after `+getId(): Long`. Keep `-id: Long` private. `CsvRepository.save()` uses this method to assign an ID when a new entity has `id = null`.
2. In `05_CSV_Repository_BaseEntity.mmd`, add the same `+setId(id: Long): void` to its `BaseEntity` class. This is the same Java class shown on a second diagram page, not a second setter.
3. In `05_CSV_Repository_BaseEntity.mmd`, add `CSV_ERROR` to the existing `ErrorCode` enumeration. Its Java value is `CSV_ERROR("E004", "CSV Error")`; it identifies file and CSV format failures. Keep the three existing enum values.

If the presentation also uses a draw.io copy of these boxes, apply the same two additions there. Do not change relationship arrows or create extra class boxes for them.

## Code fixes that do not require diagram changes

- `CsvRepository.findAll()` now records each ID when reading, so a valid row is accepted and a duplicate ID is rejected. `save()` assigns `max + 1` when `id` is null and restores the original ID if writing fails. These use method names already present in the diagram.
- `SectionRepository` reads CSV columns in the same order it writes them: `id,stadiumId,name`.
- The Java class/file is `SeatRepository.java`, with method `findBySectionId(sectionId: Long)`, matching the existing diagram. The previous lowercase class name and `getSeatsBySectionId` are not diagram changes to keep.
- `StadiumRepository.formatLine()` rejects commas, quotes, and line breaks, matching the supported CSV format; the public method name remains `formatLine`.
- `CsvRepository.writeAll()` and each `isValidText()` are private implementation helpers. They do not introduce a new use case or public API; omit them from the presentation diagram unless it is meant to list every private helper.

## Before marking Stage 03 complete

- Check that the Java code passes the Stage 03 demo and repository tests.
- Add only the three diagram lines listed above. Do not mark Administrator CRUD or any later stage complete based on repository persistence alone.
