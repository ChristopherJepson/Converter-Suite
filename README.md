# 🎧 Game Asset Audio Converter

A robust, Java-based batch processing tool designed to optimize audio assets for game development pipelines. This utility automates the conversion of raw audio files into engine-ready formats, ensuring the balance between audio fidelity and runtime performance.

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.8-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![JAVE2](https://img.shields.io/badge/FFmpeg-Wrapper-0078D7?style=for-the-badge&logo=ffmpeg&logoColor=white)

## 🎯 Project Motivation
In modern game development (Unity, Unreal Engine, Godot, Defold), audio format matters significantly for performance:

* **Background Music (BGM):** Large files must be compressed to save disk space and RAM. `.ogg` (Vorbis) is the industry standard for streaming audio due to its high quality-to-size ratio.
* **Sound Effects (SFX):** Short, frequent sounds (gunshots, UI clicks) require zero-latency decoding. Uncompressed `.wav` (PCM) is preferred here to reduce CPU overhead during playback.

This tool automates the preparation of these assets, converting raw inputs into the appropriate target format for the specific use case.

## ✨ Features
* **WAV to OGG:** Compresses uncompressed masters into stream-ready background music.
* **MP3 to OGG:** Transcodes standard audio into loop-friendly game assets.
* **OGG to WAV:** Decodes compressed assets back to PCM for editing or low-latency SFX use.
* **Smart Detection:** Automatically skips conversion steps if input files are missing, preventing runtime crashes.
* **FFmpeg Integration:** Uses the JAVE2 wrapper to leverage the power of FFmpeg without requiring manual binary installation.

## 🛠️ Tech Stack
* **Language:** Java 17 (LTS)
* **Build Tool:** Apache Maven
* **Core Library:** JAVE2 (Java Audio Video Encoder)
* **Testing:** JUnit 4.13

## 🚀 Getting Started

### Prerequisites
* [Java Development Kit (JDK) 17](https://adoptium.net/) or higher.
* [VS Code](https://code.visualstudio.com/) (Recommended) with the "Extension Pack for Java".

### Installation
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/YOUR_USERNAME/converter.git](https://github.com/YOUR_USERNAME/converter.git)
    cd converter
    ```

2.  **Build the project:**
    Maven will automatically download the required dependencies (JAVE2/FFmpeg) and compile the application.
    ```bash
    mvn clean install
    ```

## 📖 How to Use

This application is designed as a drop-in batch processor.

1.  **Place your source files** in the root directory of the project (same level as `pom.xml`).
    * Rename your music file to `input.wav` or `input.mp3`.
    * Rename your compressed asset to `input.ogg`.

2.  **Run the Application:**
    You can run it via VS Code's "Run" button or the terminal:
    ```bash
    mvn exec:java -Dexec.mainClass="com.audio.AudioConverterApp"
    ```

3.  **Retrieve Output:**
    The converted files will appear in the same directory:
    * `output_from_wav.ogg`
    * `output_from_mp3.ogg`
    * `output_from_ogg.wav`

## 🧪 Testing
The project includes a robust unit test suite that programmatically generates dummy audio headers to verify conversion logic without needing external assets.

To run the tests:
```bash
mvn test
```

---

## 📂 Project Structure

```text
converter/
├── src/
│   ├── main/java/com/audio/
│   │   └── AudioConverterApp.java     # Main entry point and logic
│   └── test/java/com/audio/
│       └── AudioConverterAppTest.java # JUnit test suite
├── pom.xml                            # Maven dependencies and build config
├── .gitignore                         # Git exclusion rules
└── README.md                          # Project documentation
```

---

Created by Christopher Jepson
  

