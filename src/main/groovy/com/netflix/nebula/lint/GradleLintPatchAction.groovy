import static FileMode.Symlink
    static readFileOrSymlink(File file, FileMode mode) {
        return mode == Symlink ? [readSymbolicLink(file.toPath()).toString()] : file.readLines()
    static diffHints(String relativePath, PatchType patchType, FileMode fileMode) {
                headers += "new file mode ${fileMode.mode}"
                headers += "deleted file mode ${fileMode.mode}"
            def fileMode = patchType == Create ? (patchFixes[0] as GradleLintCreateFile).fileMode : FileMode.fromFile(file)
                    readFileOrSymlink(file, fileMode).size() == 0) : true
            def newlineAtEndOfOriginal = emptyFile ? false : fileMode != Symlink && file.text[-1] == '\n'
            if (!emptyFile) lines += readFileOrSymlink(file, fileMode)
                ${diffHints(relativePath, patchType, fileMode)}