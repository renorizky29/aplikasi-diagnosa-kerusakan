# MotoCare Implementation Plan: PDF Export & Share Results

Adding Export to PDF (`PdfDocument`) and Share Results (`Intent.ACTION_SEND`) buttons to `ResultActivity` so users can download or share their motorcycle diagnostic reports.

## User Review Required

> [!IMPORTANT]
> - **Share Results Button**: Formats diagnostic results into a clear text report and triggers Android share intent (`Intent.ACTION_SEND`).
> - **Export to PDF Button**: Generates a professional PDF document of the diagnostic report using Android's native `PdfDocument` API and saves it to device storage.

## Open Questions

- None.

## Proposed Changes

### [Result Activity & Layout]
- [MODIFY] `res/layout/activity_result.xml`: Add "Bagikan Hasil (Share)" and "Cetak ke PDF" buttons.
- [MODIFY] `ResultActivity.kt`: Implement sharing text report and generating/saving PDF document using `PdfDocument`.

## Verification Plan

### Automated Tests
- Run existing unit tests (`DiagnosticEngineTest`).

### Manual Verification
- Build and run app.
- Complete a diagnosis, click "Bagikan Hasil" to test text sharing, and click "Cetak ke PDF" to test PDF document generation and saving.
