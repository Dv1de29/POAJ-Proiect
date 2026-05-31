#!/usr/bin/env bash
set -e

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$ROOT_DIR/src/championsLeague"
OUT_DIR="$ROOT_DIR/out"

# All .jar files in lib/ joined with ':' (the classpath separator on macOS/Linux)
LIBS="$(find "$ROOT_DIR/lib" -name '*.jar' 2>/dev/null | paste -sd ':' -)"

mkdir -p "$OUT_DIR"

find "$SRC_DIR" -name "*.java" > "$OUT_DIR/.sources.txt"
javac -d "$OUT_DIR" -cp "$LIBS" -sourcepath "$SRC_DIR" @"$OUT_DIR/.sources.txt"
rm -f "$OUT_DIR/.sources.txt"

cd "$ROOT_DIR"
java -cp "$OUT_DIR:$LIBS" Main "$@"
