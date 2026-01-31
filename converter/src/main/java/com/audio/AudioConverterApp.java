package com.audio;

import java.io.File;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class AudioConverterApp {
    public static void main(String[] args) {
    // --- 1. WAV to OGG ---
    File wavInput = new File("input.wav");
    if (wavInput.exists()) {
        System.out.println("Found input.wav! Converting to OGG...");
        try {
            convert(wavInput, new File("output_from_wav.ogg"), "ogg");
        } catch (Exception e) { System.err.println("WAV conversion failed: " + e.getMessage()); }
    } else {
        System.out.println("Skipping WAV conversion (input.wav not found).");
    }

    // --- 2. MP3 to OGG ---
    File mp3Input = new File("input.mp3");
    if (mp3Input.exists()) {
        System.out.println("Found input.mp3! Converting to OGG...");
        try {
            convert(mp3Input, new File("output_from_mp3.ogg"), "ogg");
        } catch (Exception e) { System.err.println("MP3 conversion failed: " + e.getMessage()); }
    } else {
        System.out.println("Skipping MP3 conversion (input.mp3 not found).");
    }

    // --- 3. OGG to WAV ---
    // Change "input.ogg" to the actual name of your file if it's different!
    File oggInput = new File("input.ogg"); 
    if (oggInput.exists()) {
        System.out.println("Found input.ogg! Converting to WAV...");
        try {
            convert(oggInput, new File("output_from_ogg.wav"), "wav");
        } catch (Exception e) { System.err.println("OGG conversion failed: " + e.getMessage()); }
    } else {
        System.out.println("Skipping OGG conversion (input.ogg not found).");
    }

    System.out.println("----------------------------------");
    System.out.println("Batch processing finished.");
    }

    /**
     * Generic method to convert audio files.
     *
     * @param source The source file (input)
     * @param target The target file (output)
     * @param format The desired target format ("ogg" or "wav")
     * @throws EncoderException if conversion fails
     */
    public static void convert(File source, File target, String format) throws EncoderException {
        // Audio Attributes
        AudioAttributes audio = new AudioAttributes();
        
        // Settings specific to the output format
        if (format.equalsIgnoreCase("ogg")) {
            audio.setCodec("libvorbis"); // Standard OGG codec
            audio.setBitRate(128000);    // 128 kbps
            audio.setChannels(2);
            audio.setSamplingRate(44100);
        } 
        else if (format.equalsIgnoreCase("wav")) {
            audio.setCodec("pcm_s16le"); // Standard PCM WAV codec
            // WAV usually doesn't require bitrate settings as it's uncompressed
        } 
        else {
            throw new IllegalArgumentException("Unsupported output format: " + format);
        }

        // Encoding Attributes
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat(format);
        attrs.setAudioAttributes(audio);

        // Perform the encoding
        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(source), target, attrs);
    }
}
