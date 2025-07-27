import os
import json
from collections import OrderedDict

def reorder_json_fields():
    """Iterates through subject directories and moves 'id' and 'title' to the top of each JSON file."""
    assets_dir = os.path.join('android', 'app', 'src', 'main', 'assets')
    subjects = ['english', 'maths', 'science', 'social_science']

    for subject in subjects:
        subject_dir = os.path.join(assets_dir, subject)
        if not os.path.isdir(subject_dir):
            print(f"Directory not found: {subject_dir}")
            continue

        for filename in os.listdir(subject_dir):
            if filename.endswith('.json'):
                file_path = os.path.join(subject_dir, filename)
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        data = json.load(f, object_pairs_hook=OrderedDict)

                    if 'id' in data and 'title' in data:
                        new_data = OrderedDict()
                        new_data['id'] = data.pop('id')
                        new_data['title'] = data.pop('title')
                        new_data.update(data)

                        with open(file_path, 'w', encoding='utf-8') as f:
                            json.dump(new_data, f, indent=4)
                        print(f"Reordered fields in {filename}")
                    else:
                        print(f"'id' or 'title' not found in {filename}, skipping.")

                except (json.JSONDecodeError, IOError) as e:
                    print(f"Error processing {filename}: {e}")

if __name__ == "__main__":
    reorder_json_fields()
