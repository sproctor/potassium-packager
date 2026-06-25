package com.seanproctor.potassium.internal

import com.seanproctor.potassium.dsl.ReleaseChannel
import com.seanproctor.potassium.dsl.TargetFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Pins the union-manifest contract of [UpdateYmlMerger].
 *
 * A single `<channel>-linux.yml` (keyed by OS, see [TargetFormat.updateYmlFilename]) must list
 * **every** Linux artifact so each format's client finds its file. Potassium now builds all of a
 * platform's formats in one electron-builder invocation that emits a single multi-artifact manifest,
 * but the merger remains the safety net that combines any separately-written single-artifact
 * manifests (e.g. multiple architectures) into that one union — guaranteeing, for example, that the
 * AppImage entry is never clobbered by the deb. This test pins that the merged manifest lists both.
 */
class LinuxPerFormatManifestCollisionTest {
    private val appImageFileName = "Example-1.2.3.AppImage"
    private val debFileName = "example_1.2.3_amd64.deb"

    @Test
    fun `merged linux manifest keeps every format so the AppImage is not clobbered by the deb`() {
        val channel = ReleaseChannel.Beta

        // Collision precondition: the two Linux formats resolve to the SAME manifest key, so their
        // independent electron-builder runs would otherwise publish to the same location.
        assertEquals(
            "Deb and AppImage publish to the same manifest key, so per-format runs clobber each other",
            TargetFormat.AppImage.updateYmlFilename(channel),
            TargetFormat.Deb.updateYmlFilename(channel),
        )

        // What each per-format electron-builder run leaves in its own output dir: a manifest that
        // lists ONLY that format's artifact.
        val appImageRunManifest = perFormatManifest(appImageFileName, sha512 = "AAAA==", size = 111)
        val debRunManifest = perFormatManifest(debFileName, sha512 = "BBBB==", size = 222)

        // The fix: instead of each run clobbering the shared key, the per-format manifests are
        // merged before publishing.
        val publishedManifest = UpdateYmlMerger.merge(listOf(appImageRunManifest, debRunManifest))
        val publishedFiles = filesUrls(publishedManifest)

        assertTrue("Expected the deb in $publishedFiles", debFileName in publishedFiles)
        assertTrue(
            "Published manifest must list $appImageFileName, but only contains $publishedFiles " +
                "— the merge dropped the AppImage entry.",
            appImageFileName in publishedFiles,
        )
    }

    /** A single-artifact `<channel>-linux.yml` as produced by one electron-builder format run. */
    private fun perFormatManifest(fileName: String, sha512: String, size: Long): String =
        """
        version: 1.2.3
        files:
          - url: $fileName
            sha512: $sha512
            size: $size
        path: $fileName
        sha512: $sha512
        releaseDate: '2026-05-29T00:00:00.000Z'
        """.trimIndent() + "\n"

    /** Extracts the `files[].url` values from an electron-builder manifest. */
    private fun filesUrls(manifest: String): List<String> =
        manifest.lineSequence()
            .map { it.trim() }
            .filter { it.startsWith("- url: ") }
            .map { it.removePrefix("- url: ") }
            .toList()
}
