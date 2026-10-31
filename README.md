# DZ MC

**An Open Source Minecraft Launcher Written In Java**

Notice: This is a small project, updates may be slow, and bugs are likely.
To be entirely honest this was to fix my own problems with Minecraft launchers.

**Additionally**; if you want to launch Minecraft on this, you'll need to replace some stuff in the preferences.json, such as the uid. The built version will use a dummy value until authentication is implemented.

### Implemented
- Versions
- Users

### Yet to implement
- Instance support
- Mod management
- Online mode (currently runs on offline mode)
- custom fx CSS support

## How to build from source
I've left this here for anyone(if any at all), ever need to build my code.

First, get maven to package it

`./mvnw.cmd clean package`

Once its done, find the modules using `jdep`

```shell
jdeps --module-path "$env:JAVA_HOME\jmods" --print-module-deps "target/<your source version>.jar"
```
And then, use `jlink` along with those modules to create a mini runtime

```shell
jlink `
  --module-path "$env:JAVA_HOME\jmods" `
  --add-modules <the dependancies outputted by jdep> `
  --output "runtime"
```

Finally, package it using `jpackage`

```shell
jpackage `
  --type app-image `
  --name "Launcher" `
  --input "target" `
  --main-jar "<your version>.jar" `
  --runtime-image "runtime" `
  --dest "dist"
```
