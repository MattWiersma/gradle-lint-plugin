    private static determinePatchType(List<GradleLintFix> patchFixes) {
    private static readFileOrSymlink(File file, FileMode mode) {
    private static diffHintsWithMargin(String relativePath, PatchType patchType, FileMode fileMode) {
            def (individualFixes, combinedFixes) = fileFixes.split { it instanceof RequiresOwnPatchset }
            individualFixes.each {
                patchSets.add([it] as List<GradleLintFix>)
            if(combinedFixes)
                patchSets.add((combinedFixes as List<GradleLintFix>).sort { it.from() })
            boolean overlap = true
            while(overlap) {
                patchSet.eachWithIndex { fix, i ->
                    if (i < patchSet.size() - 1) {
                        def next = patchSet[i + 1]
                        def involvesAnInsertion = fix.from() > fix.to() || next.from() > next.to()

                        if ((fix.from() <= next.from() && fix.to() >= next.to() ||
                                next.from() <= fix.from() && next.to() >= fix.to()) &&
                                !involvesAnInsertion) {
                            next.markAsUnfixed(UnfixedViolationReason.OverlappingPatch)
                        }
                overlap = patchSet.retainAll { it.reasonForNotFixing == null }
                    def beforeContext
                    if(j == 0) {
                        def firstLine = Math.max(fix.from() - 3, 1)
                        beforeContext = lines.subList(firstLine, fix.from())
                    }
                    else {
                        try {
                            beforeContext = lines.subList(patchFixes[j - 1].to() + 1, fix.from())
                        } catch(IllegalArgumentException e) {
                            throw new RuntimeException("tried to overlay patches with ranges [${patchFixes[j-1].from()}, ${patchFixes[j-1].to()}], [${fix.from()}, ${fix.to()}]", e)
                        }
                    }
                    beforeContext = beforeContext
                    if(j == 0) {
                        StringUtils.isNotBlank(line) ? '+' + line : null
                if (fix.to() < lines.size() - 1 && lastFix) {
                    def lastLineOfContext = Math.min(fix.to() + 3 + 1, lines.size())
                    if (lastLineOfContext == lines.size() && !newlineAtEndOfOriginal) {
                ${diffHintsWithMargin(relativePath, patchType, fileMode)}