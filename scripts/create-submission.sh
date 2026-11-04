#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -ne 3 ]; then
  echo "Usage: $0 <milestone: 1|2> <report.pdf> <submission-manifest.txt>"
  exit 1
fi

milestone="$1"
report="$2"
manifest="$3"

if [ "$milestone" != "1" ] && [ "$milestone" != "2" ]; then
  echo "Milestone must be 1 or 2."
  exit 1
fi

for file in "$report" "$manifest"; do
  if [ ! -f "$file" ]; then
    echo "Missing file: $file"
    exit 1
  fi
done

git rev-parse --is-inside-work-tree >/dev/null
git diff --quiet && git diff --cached --quiet || {
  echo "Working tree has uncommitted changes. Commit or remove them first."
  exit 1
}

out="submission/milestone-${milestone}"
rm -rf "$out"
mkdir -p "$out"

git bundle create "$out/repository.bundle" --all
git bundle verify "$out/repository.bundle"
cp "$report" "$out/report.pdf"
cp "$manifest" "$out/submission-manifest.txt"

(
  cd "$out"
  zip -q "../milestone-${milestone}-submission.zip" repository.bundle report.pdf submission-manifest.txt
)

echo "Created submission/milestone-${milestone}-submission.zip"
