import java.nio.file.Files

    @Rule
    TemporaryFolder temp
            diff --git a/my.txt b/my.txt
    def 'delete file patch'() {
        setup:
        def f = temp.newFile('my.txt')

        f.text = '''\
        a
        '''.substring(0).stripIndent()

        when:
        def fix = new GradleLintDeleteFile(f)
        def patch = new GradleLintPatchAction(project).patch([fix])

        then:
        patch == '''
            diff --git a/my.txt b/my.txt
            deleted file mode 100644
            --- a/my.txt
            +++ /dev/null
            @@ -1,1 +0,0 @@
            -a
             '''.substring(1).stripIndent()
    }

    def 'create regular file patch'() {
        setup:
        def f = new File(project.rootDir, 'my.txt')

        when:
        def fix = new GradleLintCreateFile(f, 'hello')
        def patch = new GradleLintPatchAction(project).patch([fix])

        then:
        patch == '''
            diff --git a/my.txt b/my.txt
            new file mode 100644
            --- /dev/null
            +++ b/my.txt
            @@ -0,0 +1,1 @@
            +hello
            \\ No newline at end of file
             '''.substring(1).stripIndent()
    }

    def 'replaceAll patch'() {
        setup:
        def f = temp.newFile('my.txt')

        f.text = '''\
        a
        b
        c
        '''.substring(0).stripIndent()

        def changes = '''\
        hello
        multiline
        '''.substring(0).stripIndent()

        when:
        def lines = f.readLines()
        def fix = new GradleLintReplaceWith(f, 1..lines.size(), 1, lines[-1].length() + 1, changes)
        def patch = new GradleLintPatchAction(project).patch([fix])

        then:
        patch == '''
            diff --git a/my.txt b/my.txt
            --- a/my.txt
            +++ b/my.txt
            @@ -1,3 +1,2 @@
            -a
            -b
            -c
            +hello
            +multiline
             '''.substring(1).stripIndent()
    }

    def 'create executable file patch'() {
        setup:
        def f = new File(project.rootDir, 'exec.sh')
        f.text = 'execute me'

        when:
        def fix = new GradleLintCreateFile(f, 'hello', FileType.Executable)
        def patch = new GradleLintPatchAction(project).patch([fix])

        then:
        patch == '''
            diff --git a/exec.sh b/exec.sh
            new file mode 100755
            --- /dev/null
            +++ b/exec.sh
            @@ -0,0 +1,1 @@
            +hello
            \\ No newline at end of file
             '''.substring(1).stripIndent()
    }

    def 'delete symlink and replace with executable'() {
        setup:
        def f = temp.newFile('real.txt')
        f.text = 'hello world'
        def symlink = new File(project.rootDir, 'gradle')
        Files.createSymbolicLink(symlink.toPath(), f.toPath())

        when:
        def delete = new GradleLintDeleteFile(symlink)
        def create = new GradleLintCreateFile(new File(project.rootDir, 'gradle/some/dir.txt'), 'new file', FileType.Executable)
        def patch = new GradleLintPatchAction(project).patch([delete, create])

        then:
        patch == """\
            diff --git a/gradle b/gradle
            deleted file mode 120000
            --- a/gradle
            +++ /dev/null
            @@ -1,1 +0,0 @@
            -${f.absolutePath}
            \\ No newline at end of file
            diff --git a/gradle/some/dir.txt b/gradle/some/dir.txt
            new file mode 100755
            --- /dev/null
            +++ b/gradle/some/dir.txt
            @@ -0,0 +1,1 @@
            +new file
            \\ No newline at end of file
            """.substring(0).stripIndent()
    }


    def 'delete symlink and create file patch'() {
        setup:
        def f = temp.newFile('real.txt')
        f.text = 'hello world'
        def symlink = new File(project.rootDir, 'gradle')
        Files.createSymbolicLink(symlink.toPath(), f.toPath())

        when:
        def delete = new GradleLintDeleteFile(symlink)
        def create = new GradleLintCreateFile(new File(project.rootDir, 'gradle/some/dir.txt'), 'new file')
        def patch = new GradleLintPatchAction(project).patch([delete, create])

        then:
        patch == """\
            diff --git a/gradle b/gradle
            deleted file mode 120000
            --- a/gradle
            +++ /dev/null
            @@ -1,1 +0,0 @@
            -${f.absolutePath}
            \\ No newline at end of file
            diff --git a/gradle/some/dir.txt b/gradle/some/dir.txt
            new file mode 100644
            --- /dev/null
            +++ b/gradle/some/dir.txt
            @@ -0,0 +1,1 @@
            +new file
            \\ No newline at end of file
            """.substring(0).stripIndent()
    }

    def 'delete and create patches'() {
        setup:
        def f = temp.newFile('my.txt')

        f.text = '''\
        a
        b
        c
        '''.substring(0).stripIndent()

        when:
        def delFix = new GradleLintDeleteFile(f)
        def createFix = new GradleLintCreateFile(f, 'hello')
        def patch = new GradleLintPatchAction(project).patch([delFix, createFix])

        then:
        patch == '''
            diff --git a/my.txt b/my.txt
            deleted file mode 100644
            --- a/my.txt
            +++ /dev/null
            @@ -1,3 +0,0 @@
            -a
            -b
            -c
            diff --git a/my.txt b/my.txt
            new file mode 100644
            --- /dev/null
            +++ b/my.txt
            @@ -0,0 +1,1 @@
            +hello
            \\ No newline at end of file
             '''.substring(1).stripIndent()
    }

            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
        generator.patch([new GradleLintDeleteLines(f, 1..1)]) == expect
            diff --git a/my.txt b/my.txt
        generator.patch([new GradleLintDeleteLines(f, 1..1)]) == expect
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
        def fix2 = new GradleLintDeleteLines(f, 3..3)
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
            diff --git a/my.txt b/my.txt
