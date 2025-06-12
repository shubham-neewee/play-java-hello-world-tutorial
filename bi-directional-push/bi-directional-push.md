Here's a complete list of Git commands to enable bi-directional sync between:

# Main account: shubham-neewee


 # # ✅ One-Time Setup (Bi-Directional Sync)
# 1. Clone the Repo from Main
```
git clone https://github.com/shubham-neewee/play-java-hello-world-tutorial.git
cd play-java-hello-world-tutorial
```

# 2. Add the Secondary Remote
```
git remote add secondary https://github.com/shubhamgour-neewee/play-java-hello-world-tutorial.git
```

# 3. Add the Main Remote (optional safety)
You already have origin, but to make it explicit:
```
git remote set-url origin https://github.com/shubham-neewee/play-java-hello-world-tutorial.git
```

# 🟢 SYNC from MAIN → SECONDARY
After making changes in the main repo:
```
git push secondary --all     # Push all branches
git push secondary --tags    # Push all tags
```

# 🔄 SYNC from SECONDARY → MAIN
After making changes in the secondary repo:
```
git push origin --all        # Push all branches back to main
git push origin --tags       # Push tags
```
