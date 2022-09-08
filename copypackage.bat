%echo off
echo %1
echo %2
set p=%1\src\%2\*.java
echo %p%
copy /y nul > output.txt
for %%y in (%p%) do (
	for /f "delims=" %%l in (%%y) do echo(%%l >> output.txt
)