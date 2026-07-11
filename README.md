# Macaw Mod

Adds a passive **Macaw** bird to Minecraft 1.20.1 (Fabric):

- Spawns naturally in **jungle biomes**.
- Tame it by feeding it **seeds** (wheat, melon, pumpkin, or beetroot seeds).
- Right-click a tamed Macaw to have it **hop onto your shoulder**.
- While a Macaw rides your shoulder, you gain **Night Vision**.

Right-clicking again or normal play will drop it back off.

---

## How to build the mod for FREE using GitHub (no Java install needed)

You do **not** need to install Java or Gradle. GitHub will build the `.jar` for you in the cloud.

### Step-by-step

1. **Create a GitHub account** at https://github.com (free).
2. Click the **+** in the top-right → **New repository**. Give it any name → **Create repository**.
3. On the new empty repo page, click the link **"uploading an existing file"**.
4. **Extract the ZIP** you downloaded (double-click it) so you get a normal folder.
5. **macOS users — IMPORTANT:** the `.github` folder is **invisible** in Finder by default.
   Press **Cmd + Shift + .** (period) in Finder to **show hidden files**.
   If you skip this, the `.github` folder will NOT be uploaded, the build workflow will
   never run, and you will never get a `.jar`.
6. Open the extracted folder. Select **ALL files and folders INSIDE it** — including the
   hidden **`.github`** folder — and drag them into the GitHub upload area.
   **Drag the contents, NOT the outer folder itself.**
7. Scroll down and click **Commit changes**.
8. Click the **Actions** tab at the top of your repo.
9. Wait about **2 minutes** for the build to finish (green checkmark).
10. Click the finished build run → scroll to **Artifacts** → download **mod-jar**.
11. Unzip the downloaded artifact to get your `macawmod-1.0.0.jar`.

### Install into Minecraft

1. Install **Fabric Loader** for Minecraft 1.20.1 and the **Fabric API** mod.
2. Copy the `.jar` into your `.minecraft/mods/` folder.
3. Launch Minecraft with the Fabric profile. Enjoy your Macaws!

---

## License
MIT