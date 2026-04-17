# CONTESTO PROGETTO "GIRI" - Handoff per nuova sessione Claude

## Istruzioni per Claude (leggere per primo)
Questo file descrive un progetto Android già impostato. L'utente ti passerà lo zip dei sorgenti e ti chiederà aiuto per integrarli in Android Studio. Leggi tutto prima di rispondere. Segui le userPreferences dell'utente: risposte precise, concise, senza fronzoli, indicare sempre la confidenza quando non si è sicuri, mai compiacere.

---

## Obiettivo dell'app
App Android nativa per contare i giri durante allenamenti di ginnastica.

### Requisiti funzionali
1. Contatore di giri visualizzato grande a schermo
2. Pulsante "+1 giro" (incrementa)
3. Pulsante "-1" (decrementa, non va sotto zero)
4. Pulsante "RESET" (azzera)
5. Comandi vocali **offline** in italiano:
   - "più" / "su" / "giro" / "avanti" / "uno" → incrementa
   - "meno" / "giù" / "indietro" → decrementa
   - "reset" / "azzera" / "zero" / "ricomincia" → azzera
6. Schermo sempre acceso mentre l'app è in primo piano (FLAG_KEEP_SCREEN_ON)
   - NON serve funzionare in background / con telefono in tasca
   - L'utente tiene il telefono visibile sul tappetino/panca

---

## Decisioni tecniche prese

| Decisione | Scelta | Motivazione |
|-----------|--------|-------------|
| Linguaggio | Kotlin | Standard Android, app semplice |
| Stack | Android nativo (no Flutter/RN) | Accesso diretto e affidabile a microfono e wake lock; app piccola |
| Riconoscimento vocale | Vosk offline, modello italiano small | Ascolto continuo, funziona senza internet, utente voleva offline |
| Wake lock | FLAG_KEEP_SCREEN_ON | Sufficiente per uso in primo piano, no foreground service |
| Min SDK | 24 (Android 7.0) | Copertura ampia |
| Target SDK | 34 (Android 14) | Aggiornato |
| Build tool | Gradle 8.5 + AGP 8.2.2 | Compatibile con Android Studio Hedgehog/Iguana+ |
| JDK | 17 | Richiesto da AGP 8.2+ |

### Dipendenze principali (app/build.gradle)
```
com.alphacephei:vosk-android:0.3.47@aar
net.java.dev.jna:jna:5.13.0@aar
androidx.core:core-ktx:1.12.0
androidx.appcompat:appcompat:1.6.1
com.google.android.material:material:1.11.0
```

### Repository Maven aggiuntivo necessario
In `build.gradle` root è aggiunto: `maven { url 'https://alphacephei.com/maven/' }`

---

## Struttura del progetto

```
GiriApp/
├── build.gradle                    # Root build config
├── settings.gradle                 # include ':app'
├── gradle.properties               # org.gradle.jvmargs, useAndroidX
├── .gitignore
├── README.md
├── app/
│   ├── build.gradle                # App-level config
│   └── src/main/
│       ├── AndroidManifest.xml     # Permessi: RECORD_AUDIO, INTERNET
│       ├── java/com/example/giriapp/
│       │   └── MainActivity.kt     # Tutta la logica
│       ├── res/
│       │   ├── layout/activity_main.xml
│       │   ├── values/strings.xml
│       │   ├── values/themes.xml
│       │   ├── drawable/ic_launcher_foreground.xml
│       │   ├── drawable/ic_launcher_background.xml
│       │   └── mipmap-anydpi-v26/
│       │       ├── ic_launcher.xml
│       │       └── ic_launcher_round.xml
│       └── assets/model-it/        # DA AGGIUNGERE: modello Vosk italiano
└── .github/workflows/build.yml     # (Non usato se si compila con Android Studio)
```

---

## COSA MANCA NEL PROGETTO (l'utente deve aggiungerlo)

### 1. Modello vocale Vosk italiano
**CRITICO**: L'APK non funzionerà senza il modello. Va messo in `app/src/main/assets/model-it/`

- URL: https://alphacephei.com/vosk/models/vosk-model-small-it-0.22.zip
- Dimensione: ~50 MB
- Procedura:
  1. Scaricare lo zip
  2. Estrarlo
  3. Copiare tutto il CONTENUTO della cartella `vosk-model-small-it-0.22/` dentro `app/src/main/assets/model-it/`
  4. Alla fine dentro `model-it/` devono esserci cartelle come `am/`, `conf/`, `graph/`, `ivector/` ecc.

### 2. Gradle Wrapper
Il wrapper binario non è incluso nello zip. Android Studio lo genera automaticamente al primo import del progetto, oppure da terminale: `gradle wrapper`

---

## Punti critici / possibili problemi (con confidenza)

1. **Compatibilità Vosk 0.3.47** (confidenza 70%): versione scelta perché documentata funzionante. Se dà errori di linking, provare `0.3.45` o `0.3.32`.

2. **API di SpeechService/RecognitionListener** (confidenza 75%): il codice usa callback `onPartialResult`, `onResult`, `onFinalResult`, `onError`, `onTimeout`. Se Vosk ha cambiato firma metodi, l'IDE segnalerà errori da correggere.

3. **Permesso microfono** (confidenza 95%): gestito in MainActivity con richiesta a runtime.

4. **Falsi positivi vocali** (confidenza 60%): in ambiente rumoroso, il modello small può interpretare rumori come parole chiave. Le keyword sono modificabili facilmente in MainActivity.

5. **Dimensione APK**: includendo il modello Vosk, l'APK sarà ~55-60 MB. Normale per app con speech recognition offline.

---

## Cronologia decisionale della sessione precedente

1. Utente voleva app Android per contare giri → confermata fattibilità
2. Domanda su uso telefono → risposta: "sempre visibile su tappetino"
3. Domanda su voce → risposta: "offline con Vosk"
4. Domanda su Android Studio → risposta iniziale: "build cloud" (poi cambiata: ora vuole usare Android Studio)
5. Claude ha scritto tutti i sorgenti ma NON ha consegnato lo zip nella prima sessione (errore di gestione operazioni)
6. Sessione attuale: consegna zip + questo file di contesto

---

## Cosa chiedere/fare nella prossima sessione

L'utente ti passerà lo zip. Tu devi:
1. Verificare la struttura dei file
2. Guidare l'utente passo-passo su:
   - Come aprire il progetto in Android Studio (File → Open → selezionare cartella root)
   - Come fare il sync Gradle
   - Come scaricare e posizionare il modello Vosk italiano in `assets/model-it/`
   - Come collegare un telefono Android con USB debugging o creare emulatore
   - Come fare "Run" per compilare e installare l'APK
   - Dove trovare l'APK generato (`app/build/outputs/apk/debug/`)
3. Essere pronto a debuggare errori di build (dipendenze, versioni SDK, ecc.)
4. NON riscrivere il codice da zero: usare quello fornito nello zip.

### Preferenze utente (userPreferences)
- Risposte precise e concise
- Indicare percentuali di confidenza quando non sicuro
- Non compiacere, essere oggettivi
- Se non si sa qualcosa, dirlo esplicitamente
