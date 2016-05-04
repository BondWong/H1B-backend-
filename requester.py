#!/usr/bin/env python3
import requests
import json

def post(url, data):
    response = requests.post(url, data = json.dumps(data), headers = {'Content-Type': 'application/json'})
    print(response.status_code)
