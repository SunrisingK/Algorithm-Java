import os
import glob

for file_path in glob.glob('**/*.class', recursive=True):
    os.remove(file_path)
print("successfully cleared .class files")