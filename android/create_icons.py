#!/usr/bin/env python3
from PIL import Image, ImageDraw
import os

# Create directories if they don't exist
densities = {
    'mdpi': 48,
    'hdpi': 72, 
    'xhdpi': 96,
    'xxhdpi': 144,
    'xxxhdpi': 192
}

base_path = '/Users/marrow/Documents/Alpha/edify/android/app/src/main/res'

for density, size in densities.items():
    # Create square icon
    img = Image.new('RGB', (size, size), color='#2196F3')  # Material Blue
    draw = ImageDraw.Draw(img)
    
    # Add a simple 'E' for Edify
    font_size = size // 2
    draw.text((size//4, size//4), 'E', fill='white', font_size=font_size)
    
    # Save regular icon
    icon_path = f'{base_path}/mipmap-{density}/ic_launcher.png'
    img.save(icon_path)
    
    # Create round icon (same for now)
    round_path = f'{base_path}/mipmap-{density}/ic_launcher_round.png'
    img.save(round_path)
    
print('Icons created successfully')
