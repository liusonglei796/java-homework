# GitHub Repository Association Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Associate the local project with the GitHub repository and upload the Java files.

**Architecture:** Initialize a local git repository, add the remote origin, and push the files to the `main` branch.

**Tech Stack:** Git

---

### Task 1: Initialize Git and Configure .gitignore

**Files:**
- Create: `.gitignore`

- [ ] **Step 1: Initialize Git repository**

Run: `git init`
Expected: "Initialized empty Git repository in ..."

- [ ] **Step 2: Create .gitignore**

```text
# Java
*.class
*.bluej
*.pkg
*.ctxt
.mtj.tmp/
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# IDEs
.idea/
*.iml
.classpath
.project
.settings/
.vscode/
bin/

# OS
.DS_Store
Thumbs.db
```

- [ ] **Step 3: Commit .gitignore**

```bash
git add .gitignore
git commit -m "chore: add .gitignore"
```

---

### Task 2: Configure Remote and Upload Files

**Files:**
- Modify: Local Git configuration

- [ ] **Step 1: Add remote origin**

Run: `git remote add origin https://github.com/liusonglei796/java-homework.git`

- [ ] **Step 2: Verify remote**

Run: `git remote -v`
Expected: Show fetch and push URLs for `origin`.

- [ ] **Step 3: Stage all Java files**

Run: `git add "异常处理和输入输出流/"`

- [ ] **Step 4: Commit Java files**

Run: `git commit -m "feat: initial commit of Java homework files"`

- [ ] **Step 5: Rename branch to main**

Run: `git branch -M main`

- [ ] **Step 6: Push to remote**

Run: `git push -u origin main`
Expected: Successful push to GitHub.
