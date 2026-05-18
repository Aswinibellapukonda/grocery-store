import json
import re

with open('images.json', 'r') as f:
    img_map = json.load(f)

with open('src/main/java/com/grocerystore/component/DataSeeder.java', 'r') as f:
    content = f.read()

# Build the java map initialization
map_code = 'Map<String, String> imgMap = new HashMap<>();\n'
for k, v in img_map.items():
    map_code += f'            imgMap.put("{k}", "{v}");\n'

# Find where to inject it
target = 'List<Product> products = new ArrayList<>();'
new_content = content.replace(target, map_code + '            ' + target)

# Replace the imageUrl generation logic
old_logic = '''String encodedName = itemName.replace(" ", "%20");
                    String imageUrl = "https://image.pollinations.ai/prompt/a%20high%20quality%20photo%20of%20fresh%20grocery%20" + encodedName + "?width=400&height=400&nologo=true";'''

new_logic = 'String imageUrl = imgMap.getOrDefault(itemName, "https://placehold.co/400x400/e2e8f0/1e293b?text=" + itemName.replace(" ", "%20"));'

new_content = new_content.replace(old_logic, new_logic)

with open('src/main/java/com/grocerystore/component/DataSeeder.java', 'w') as f:
    f.write(new_content)
print('Updated DataSeeder.java')
