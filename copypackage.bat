%echo off
set p=%1\src\%2\*.java
echo copying items matching %p% to output.txt
copy /y nul > output.txt
for %%y in (%p%) do (
	for /f "delims=" %%l in (%%y) do echo(%%l >> output.txt
)