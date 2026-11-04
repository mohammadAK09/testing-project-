# Moodle Submission Guide

## 1. Prepare the final repository state

```bash
mvn clean test
git status
git log -1 --format=%H
```

Record the displayed commit hash in the manifest and report. Push the final commit to GitHub.

## 2. Create and verify the Git bundle

From the repository root:

```bash
git bundle create repository.bundle --all
git bundle verify repository.bundle
```

## 3. Prepare the ZIP

The final ZIP must contain:

```text
repository.bundle
report.pdf
submission-manifest.txt
```

When material AI use is declared, include the completed AI Use Log as an appendix in `report.pdf`.

You may use the provided `scripts/create-submission.sh` on macOS/Linux or `scripts/create-submission.ps1` on Windows PowerShell.

## 4. Final verification

- Open the ZIP.
- Confirm all three required files are present.
- Confirm the manifest identifies the correct milestone and commit hash.
- Confirm the uploaded Moodle file is the final ZIP.
