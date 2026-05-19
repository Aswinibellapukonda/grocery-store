import json
import urllib.request

url = 'http://localhost:8081/api/products'
text = urllib.request.urlopen(url).read().decode()
data = json.loads(text)
targets = ['Mangoes','Watermelon','Pineapple','Apple','Milk','Dish Wash Liquid','Frozen Fries']
for name in targets:
    p = next((x for x in data if x['productName'] == name), None)
    print(name, '->', p['imageUrl'] if p else 'MISSING')
