# TP-Link Router Auto Configuration Tool

This Python script allows you to **automatically configure TP-Link Wi-Fi routers** (especially after a reset or power outage) by programmatically setting up SSID, channel, password, and other WiFi parameters for interfaces like `wlan0` and `wlan1`.

---

## 🚀 Features

- Automatically connects to TP-Link router web interface
- Sets WiFi name (SSID), password, and encryption mode
- Supports multiple bands (`wlan0` for 2.4GHz, `wlan1` for 5GHz if applicable)
- Enables WPA2 AES encryption
- Configurable channel, bandwidth, and domain



## 🛠 Requirements

Install dependencies with:

```bash
pip install -r requirements.txt

requirements.txt content:

requests>=2.25.1
urllib3>=1.26.0


📁 Project Structure

├── tplink_config.py        # Main Python script
├── requirements.txt        # Python dependencies
├── README.md               # Project overview and usage

🔧 How to Use
1. Reset your TP-Link router and connect to its default Wi-Fi network (usually something like TP-  LINK_XXXX).

2. Make sure the default IP is accessible (typically 192.168.0.1 or 192.168.1.1).

3. Open tplink_config.py and edit the wlan0_config and wlan1_config dictionaries with your desired settings:

wlan0_config = {
    'SSID': 'MyCustomNetwork',
    'WPAEncryptionModes': 'AESEncryption',
    'X_TP_PreSharedKey': 'your_password_here',
    'Channel': 'auto',
    'X_TP_Bandwidth': '20',
    'X_TP_APIsolationEnabled': 'false',
    'X_TP_Region': 'Pakistan'
}

wlan1_config = {
    'SSID': 'MyCustomNetwork_5G',
    'WPAEncryptionModes': 'AESEncryption',
    'X_TP_PreSharedKey': 'your_password_here',
    'Channel': 'auto',
    'X_TP_Bandwidth': '80',
    'X_TP_APIsolationEnabled': 'false',
    'X_TP_Region': 'Pakistan'
}

4. Run the script:

bash
Copy
Edit
python tplink_config.py

❗ Notes
You may need to update the login token format or headers depending on your TP-Link model and firmware.

The script uses hardcoded values for educational simplicity — adapt it as needed.

The router must not be behind another network (e.g., bridged mode won't work).

⚠️ Disclaimer
This tool is for educational and personal use only.

It may not work with all TP-Link routers or firmware versions.

Use only on routers you own or have explicit permission to configure.

No guarantees of performance or compatibility.

📄 License
This project is released under the MIT License. No warranties provided. Use at your own risk.


