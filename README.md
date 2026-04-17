# Giri - Contatore allenamenti

App Android per contare i giri durante gli allenamenti, con comandi vocali offline.

## Funzionalità
- Contatore con pulsanti: +1, -1, Reset
- Comandi vocali offline (Vosk, italiano):
  - **"più"** / "su" / "giro" → incrementa
  - **"meno"** / "giù" → decrementa
  - **"reset"** / "azzera" → azzera
- Schermo sempre acceso mentre l'app è in primo piano

## Come ottenere l'APK (senza installare nulla)

### 1. Crea un account GitHub
Vai su https://github.com e registrati (gratis).

### 2. Crea un nuovo repository
- Clicca su "New repository"
- Nome: `giri-app` (o quello che preferisci)
- Visibilità: Public o Private
- **NON** inizializzare con README

### 3. Carica i file
Opzione A (via web, più semplice):
- Nel repository appena creato, clicca "uploading an existing file"
- Trascina TUTTI i file e cartelle di questo progetto
- Commit

Opzione B (da terminale):
```bash
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/TUO_USERNAME/giri-app.git
git push -u origin main
```

### 4. Attendi la build automatica
- Vai nella tab "Actions" del repository
- Vedrai un workflow in esecuzione (~5-10 minuti la prima volta)
- Quando diventa verde ✓, clicca sopra
- In basso trovi "Artifacts" → scarica `giri-app-debug`

### 5. Installa sul telefono
- Estrai lo zip, dentro trovi `app-debug.apk`
- Trasferisci l'APK sul telefono (email, Drive, USB, Telegram...)
- Sul telefono, apri il file: Android chiederà di abilitare "origini sconosciute"
- Concedi l'autorizzazione e installa
- Al primo avvio concedi il permesso microfono

## Note importanti

- È un APK **debug** non firmato, solo per uso personale. Android lo installerà ma lo marcherà come "di origine sconosciuta". Va bene.
- Al primo avvio il modello vocale viene estratto dagli asset (~50 MB), può richiedere 5-10 secondi.
- Se i comandi vocali non funzionano bene in ambiente rumoroso, puoi modificare le parole chiave in `MainActivity.kt` (variabili `incrementKeywords`, `decrementKeywords`, `resetKeywords`).
