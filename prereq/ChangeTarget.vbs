Set sh = CreateObject("WScript.Shell")
Set shortcut = sh.CreateShortcut("")
shortcut.TargetPath = ""
shortcut.Arguments = "--url=zoommtg://zoom.us/join?action=join&confno=&pwd="
shortcut.Save