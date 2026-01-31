package com.audio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import ws.schild.jave.EncoderException;

public class AudioConverterAppTest {

    private File sourceWav;
    private File targetOgg;
    private File targetWav;

    @Before
    public void setUp() {
        // defined file paths for testing
        sourceWav = new File("test_audio.wav");
        targetOgg = new File("test_output.ogg");
        targetWav = new File("test_output.wav");
    }

    @After
    public void tearDown() {
        // Cleanup: Delete temporary files after every test
        if (sourceWav.exists()) sourceWav.delete();
        if (targetOgg.exists()) targetOgg.delete();
        if (targetWav.exists()) targetWav.delete();
    }

    /**
     * Test 1: The "Happy Path"
     * We create a REAL valid minimal WAV file (programmatically),
     * convert it, and ensure the output file is created.
     */
    @Test
    public void testWavToOggConversion() throws IOException, EncoderException {
        // 1. Create a dummy WAV file
        createDummyWavFile(sourceWav);

        // 2. Perform conversion
        System.out.println("Testing WAV -> OGG...");
        AudioConverterApp.convert(sourceWav, targetOgg, "ogg");

        // 3. Verify output exists and has data
        assertTrue("Output OGG file should exist", targetOgg.exists());
        assertTrue("Output OGG file should not be empty", targetOgg.length() > 0);
    }

    /**
     * Test 2: Invalid Format
     * Ensuring the app throws IllegalArgumentException if we ask for "mp4" or "txt".
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUnsupportedFormat() throws EncoderException, IOException {
        createDummyWavFile(sourceWav);
        
        // This should fail immediately because "mp4" is not handled in your if/else logic
        AudioConverterApp.convert(sourceWav, new File("out.mp4"), "mp4");
    }

    /**
     * Test 3: Missing Source File
     * Ensuring the JAVE library throws an EncoderException if the input file is missing.
     */
    @Test(expected = EncoderException.class)
    public void testMissingSourceFile() throws EncoderException {
        File nonExistentFile = new File("ghost_file.wav");
        
        // This should fail because the file doesn't exist
        AudioConverterApp.convert(nonExistentFile, targetOgg, "ogg");
    }

    // --- Helper Method to Create a Valid WAV Header ---
    private void createDummyWavFile(File file) throws IOException {
        // This is a minimal valid WAV header (44 bytes) + 0 bytes of audio data.
        // It allows FFMPEG to recognize the file as a valid WAV container.
        byte[] wavHeader = new byte[] {
            0x52, 0x49, 0x46, 0x46, // "RIFF"
            0x24, 0x00, 0x00, 0x00, // Chunk Size
            0x57, 0x41, 0x56, 0x45, // "WAVE"
            0x66, 0x6D, 0x74, 0x20, // "fmt "
            0x10, 0x00, 0x00, 0x00, // Subchunk1 Size (16 for PCM)
            0x01, 0x00,             // AudioFormat (1 = PCM)
            0x01, 0x00,             // NumChannels (1 = Mono)
            0x44, (byte) 0xAC, 0x00, 0x00, // SampleRate (44100)
            (byte) 0x88, 0x58, 0x01, 0x00, // ByteRate
            0x02, 0x00,             // BlockAlign
            0x10, 0x00,             // BitsPerSample (16)
            0x64, 0x61, 0x74, 0x61, // "data"
            0x00, 0x00, 0x00, 0x00  // Subchunk2 Size (0 bytes of data)
        };

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(wavHeader);
        }
    }
}