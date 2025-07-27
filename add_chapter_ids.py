import os
import json
import uuid

def add_uuid_to_chapters():
    """Iterates through subject directories and adds a UUID to each chapter JSON file."""
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
                    with open(file_path, 'r+', encoding='utf-8') as f:
                        data = json.load(f)
                        if 'id' not in data:
                            data['id'] = str(uuid.uuid4())
                            f.seek(0)
                            json.dump(data, f, indent=4)
                            f.truncate()
                            print(f"Added UUID to {filename}")
                        else:
                            print(f"UUID already exists in {filename}")
                except (json.JSONDecodeError, IOError) as e:
                    print(f"Error processing {filename}: {e}")

if __name__ == "__main__":
    add_uuid_to_chapters()
