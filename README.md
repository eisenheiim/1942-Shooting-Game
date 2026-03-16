# 1942 Shooting Game

A simple 2D arcade-style shooting game written in Java using `StdDraw`.

## Project Structure

- `src/Main.java`: Main version of the game.
- `src/Bonus.java`: Extended/bonus version with extra behaviors (e.g., sound effects and enhanced enemy behavior).
- `assets/`: Game images and audio files used at runtime.
- `report/SudeNazAslan.tex`: Project report in LaTeX.

## Requirements

- Java 8+ (Java 11 or newer recommended)
- `StdDraw` library (Princeton)

> Note: `StdDraw` is referenced in the source code but is not included in this repository.

## Setup

1. Add `StdDraw.java` into the `src/` folder **or** add `StdDraw.jar` to your classpath.
2. Keep the `assets/` folder at the project root (same level as `src/`).

## Compile and Run

From the project root:

### Option A: If `StdDraw.java` is in `src/`

```bash
javac src/*.java
java -cp src Main
```

To run the bonus version:

```bash
java -cp src Bonus
```

### Option B: If using `StdDraw.jar`

```bash
javac -cp "src:lib/StdDraw.jar" src/*.java
java -cp "src:lib/StdDraw.jar" Main
```

Run bonus version:

```bash
java -cp "src:lib/StdDraw.jar" Bonus
```

On macOS/Linux, classpath separator is `:`. On Windows, use `;`.

## Controls

### Menu

- `Enter`: Start game
- Arrow keys: Move player
- `Space`: Shoot
- `Q / E`: Increase / decrease player movement speed
- `A / D`: Decrease / increase FPS (by changing frame delay)

### In-Game

- Arrow keys: Move
- `Space`: Shoot
- `P`: Pause / Resume

### End Screen

- `Up / Down`: Select menu option
- `Enter`: Confirm (`Restart` or `End Game`)

## Gameplay

- Defeat enemy ships to gain score.
- Avoid enemy bullets and collisions.
- Collect falling hearts to gain extra lives.
- Win by defeating all active enemies before lives reach zero.

## Notes

- The game uses fixed-size arrays for bullets, enemies, hearts, and explosions.
- Asset paths are relative (e.g., `assets/background.png`), so run from the project root for correct loading.
