import json
from pathlib import Path

from fastapi import FastAPI
from fastapi.responses import JSONResponse, HTMLResponse


import os
DATA_DIR = Path(os.path.dirname(__file__)) / "data"
ENDPOINTS = ["users", "authors", "books", "reviews"]

app = FastAPI()


def read_json(endpoint: str) -> dict:
    file_path = DATA_DIR / f"{endpoint}.json"
    if file_path.exists():
        with open(file_path, "r", encoding="utf-8") as f:
            return json.load(f)
    return {"error": "file not found"}


@app.get("/", response_class=HTMLResponse)
def index():
    links = "".join(
        [
            f'<li><a href="/{endpoint}">{endpoint.title()}</a></li>\n'
            for endpoint in ENDPOINTS
        ]
    )
    html_content = f"""
    <!DOCTYPE html>
    <html lang="en">
      <head>
        <meta charset="UTF-8">
        <title>IE API</title>
      </head>
      <body>
        <h1>IE API (Spring 04)</h1>
        <h2>Endpoints:</h2>
        <ul>{links}</ul>
      </body>
    </html>
    """
    return HTMLResponse(content=html_content)


@app.get("/{endpoint}")
def get_json(endpoint: str):
    endpoint = endpoint.lower()
    if endpoint not in ENDPOINTS:
        return JSONResponse(
            status_code=404, content={"error": "invalid endpoint requested"}
        )
    data = read_json(endpoint)
    return JSONResponse(content=data)


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
