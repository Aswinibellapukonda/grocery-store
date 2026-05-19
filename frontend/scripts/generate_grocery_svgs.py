from pathlib import Path
import re

root = Path(__file__).resolve().parents[2]
seeder = root / 'backend' / 'src' / 'main' / 'java' / 'com' / 'grocerystore' / 'component' / 'DataSeeder.java'
outdir = root / 'frontend' / 'public' / 'images' / 'products'

text = seeder.read_text(encoding='utf-8')
pattern = re.compile(r'categoryItems\.put\("([^"]+)",\s*Arrays\.asList\(([^)]+)\)\);')
items = []
for cat, list_str in pattern.findall(text):
    names = re.findall(r'"([^"]+)"', list_str)
    for name in names:
        items.append((cat, name))

seen = set()
items_unique = []
for cat, name in items:
    if name not in seen:
        seen.add(name)
        items_unique.append((cat, name))

palette = [
    '#f8b4b4', '#fde68a', '#86efac', '#7dd3fc', '#c4b5fd',
    '#fbcfe8', '#a5b4fc', '#fde68a', '#6ee7b7', '#fb7185'
]

outdir.mkdir(parents=True, exist_ok=True)
for cat, name in items_unique:
    normalized = re.sub(r'[^a-z0-9]+', '-', name.lower()).strip('-')
    if normalized == 'mangoes':
        # keep existing photo asset if present
        continue
    target = outdir / f'{normalized}.svg'
    bg = palette[hash(normalized) % len(palette)]
    svg = f'''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 400 300">
  <rect width="400" height="300" rx="24" fill="{bg}"/>
  <text x="50%" y="45%" dominant-baseline="middle" text-anchor="middle" font-family="Segoe UI, sans-serif" font-size="34" fill="#0f172a">{name}</text>
  <text x="50%" y="70%" dominant-baseline="middle" text-anchor="middle" font-family="Segoe UI, sans-serif" font-size="18" fill="#0f172a">{cat}</text>
</svg>'''
    target.write_text(svg, encoding='utf-8')

print(f'Generated {len(items_unique)} SVG files in {outdir}')
