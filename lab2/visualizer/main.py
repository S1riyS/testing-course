import sys
import os
import csv
import matplotlib.pyplot as plt
import numpy as np


def read_csv(filepath: str) -> tuple[list[float], list[float | None], str]:
    xs: list[float] = []
    ys: list[float | None] = []
    label = ""

    with open(filepath, newline="") as f:
        reader = csv.reader(f, delimiter=";")
        header = next(reader)
        label = header[1] if len(header) > 1 else os.path.splitext(
            os.path.basename(filepath))[0]

        for row in reader:
            if len(row) < 2 or row[0].startswith("#"):
                continue
            try:
                x = float(row[0])
            except ValueError:
                continue

            y_str = row[1].strip()
            if y_str.lower() == "nan" or y_str == "":
                xs.append(x)
                ys.append(None)
            else:
                try:
                    xs.append(x)
                    ys.append(float(y_str))
                except ValueError:
                    xs.append(x)
                    ys.append(None)

    return xs, ys, label


def split_segments(xs: list[float], ys: list[float | None], threshold: float = 50.0):
    """Split into continuous segments, breaking on NaN or large jumps (asymptotes)."""
    seg_x: list[float] = []
    seg_y: list[float] = []
    segments: list[tuple[list[float], list[float]]] = []

    for x, y in zip(xs, ys):
        if y is None:
            if seg_x:
                segments.append((seg_x, seg_y))
                seg_x, seg_y = [], []
            continue

        if seg_y and abs(y - seg_y[-1]) > threshold:
            segments.append((seg_x, seg_y))
            seg_x, seg_y = [], []

        seg_x.append(x)
        seg_y.append(y)

    if seg_x:
        segments.append((seg_x, seg_y))

    return segments


def compute_ylim(ys: list[float | None], margin: float = 0.15) -> tuple[float, float] | None:
    """Compute Y-axis limits based on the 2nd and 98th percentiles with margin."""
    valid = [y for y in ys if y is not None]
    if not valid:
        return None
    arr = np.array(valid)
    lo, hi = np.percentile(arr, 8), np.percentile(arr, 98)
    pad = (hi - lo) * margin if hi != lo else 1.0
    return lo - pad, hi + pad


def plot_csv(filepath: str, ax: plt.Axes):
    xs, ys, label = read_csv(filepath)
    if not xs:
        return

    segments = split_segments(xs, ys)
    for i, (sx, sy) in enumerate(segments):
        ax.plot(sx, sy, linewidth=1.5, label=label if i == 0 else None)

    ylim = compute_ylim(ys)
    if ylim is not None:
        ax.set_ylim(ylim)

    ax.set_title(label, fontsize=13, fontweight="bold")
    ax.set_xlabel("x")
    ax.set_ylabel(label)
    ax.grid(True, alpha=0.3)
    ax.axhline(y=0, color="k", linewidth=0.5)
    ax.axvline(x=0, color="k", linewidth=0.5)


def main():
    if len(sys.argv) < 2:
        print(f"Usage: python {sys.argv[0]} <csv_directory>")
        sys.exit(1)

    csv_dir = sys.argv[1]
    if not os.path.isdir(csv_dir):
        print(f"Error: '{csv_dir}' is not a directory")
        sys.exit(1)

    csv_files = sorted(
        [f for f in os.listdir(csv_dir) if f.endswith(".csv")],
        key=lambda name: (name != "system.csv", name),
    )

    if not csv_files:
        print(f"No CSV files found in '{csv_dir}'")
        sys.exit(1)

    n = len(csv_files)
    cols = min(n, 4)
    rows = (n + cols - 1) // cols

    fig, axes = plt.subplots(rows, cols, figsize=(
        5 * cols, 3.5 * rows), squeeze=False)

    for idx, filename in enumerate(csv_files):
        r, c = divmod(idx, cols)
        filepath = os.path.join(csv_dir, filename)
        plot_csv(filepath, axes[r][c])

    for idx in range(n, rows * cols):
        r, c = divmod(idx, cols)
        axes[r][c].set_visible(False)

    fig.suptitle("Function plots", fontsize=16, fontweight="bold", y=1.01)
    fig.tight_layout()
    plt.savefig(os.path.join(csv_dir, "plots.png"),
                dpi=150, bbox_inches="tight")
    print(f"Saved combined plot to {os.path.join(csv_dir, 'plots.png')}")
    plt.show()


if __name__ == "__main__":
    main()
