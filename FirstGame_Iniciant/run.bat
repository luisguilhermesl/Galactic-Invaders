@echo off
REM ============================================================
REM  Compila e roda o jogo DIRETO, ignorando a IDE (Eclipse/NetBeans)
REM  Use este arquivo quando a IDE "nao atualizar" as mudancas.
REM ============================================================
cd /d "%~dp0"

echo.
echo === Compilando do zero ===
if not exist bin mkdir bin

REM Lista todos os .java de src\main e compila
dir /s /b src\main\*.java > __sources.txt
REM --release 21 garante que o Java 21 (runtime) consiga rodar as classes
javac --release 21 -encoding UTF-8 -d bin @__sources.txt
del __sources.txt

if errorlevel 1 (
    echo.
    echo *** ERRO DE COMPILACAO - veja as mensagens acima ***
    pause
    exit /b 1
)

echo === Rodando o jogo ===
echo.
java -cp bin main.Game

echo.
echo === Jogo encerrado ===
pause
