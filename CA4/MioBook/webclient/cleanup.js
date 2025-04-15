const { exec } = require("child_process");

const reactApp = exec("react-scripts start");

function clearLocalStorage() {
    console.log("Cleaning up local storage...");
    exec("node -e \"localStorage.clear()\"", (error) => {
        if (error) {
            console.error("Failed to clear local storage:", error);
        } else {
            console.log("Local storage cleared.");
        }
    });
}

process.on("SIGINT", () => {
    clearLocalStorage();
    process.exit();
});

process.on("SIGTERM", () => {
    clearLocalStorage();
    process.exit();
});

process.on("exit", () => {
    clearLocalStorage();
});

reactApp.stdout.pipe(process.stdout);
reactApp.stderr.pipe(process.stderr);