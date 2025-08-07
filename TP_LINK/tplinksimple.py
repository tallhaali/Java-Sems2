#!/usr/bin/env python3
"""
Simple TP-Link Router Configuration Script

This script provides a simplified approach to configure TP-Link WiFi routers.
Focuses on the most common configurations and handles errors gracefully.
"""

import requests
import json
import time
import re
from urllib.parse import urljoin
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class TPLinkSimpleConfig:
    def __init__(self, router_ip="{{ROUTER_IP}}", username="{{USERNAME}}", password="{{PASSWORD}}"):
        self.router_ip = router_ip
        self.username = username
        self.password = password
        self.base_url = f"http://{router_ip}"
        self.session = requests.Session()

        self.session.headers.update({
            'User-Agent': 'Mozilla/5.0',
            'Accept': '*/*',
            'Accept-Language': 'en-US,en;q=0.9',
            'Accept-Encoding': 'gzip, deflate',
            'Connection': 'keep-alive',
        })

        self.is_authenticated = False

    def test_connection(self):
        try:
            logger.info(f"Testing connection to {self.base_url}")
            response = self.session.get(self.base_url, timeout=10)
            if response.status_code == 200 or response.status_code == 403:
                logger.info("✅ Router is accessible")
                return True
            logger.error(f"❌ Router responded with status code: {response.status_code}")
            return False
        except requests.exceptions.RequestException as e:
            logger.error(f"❌ Cannot connect to router: {e}")
            return False

    def _find_login_page(self):
        login_endpoints = ["/", "/login", "/cgi-bin/luci", "/admin"]
        for endpoint in login_endpoints:
            try:
                logger.info(f"Trying login endpoint: {endpoint}")
                response = self.session.get(f"{self.base_url}{endpoint}", timeout=10)
                if response.status_code == 200 and any(k in response.text.lower() for k in ['login', 'username', 'password']):
                    logger.info(f"✅ Found login page at: {endpoint}")
                    return endpoint
            except:
                continue
        return "/cgi-bin/luci"

    def _extract_auth_token(self, text):
        patterns = [
            r'name="token"\s+value="([^"]+)"',
            r'name="auth_token"\s+value="([^"]+)"',
            r'"token":"([^"]+)"',
        ]
        for pattern in patterns:
            match = re.search(pattern, text)
            if match:
                token = match.group(1)
                logger.info(f"✅ Token extracted: {token[:10]}...")
                return token
        return None

    def login(self):
        login_page = self._find_login_page()
        return self._try_standard_login(login_page) or \
               self._try_luci_login(login_page) or \
               self._try_direct_login(login_page)

    def _try_standard_login(self, login_page):
        try:
            logger.info("Trying standard login method...")
            response = self.session.get(f"{self.base_url}{login_page}", timeout=10)
            token = self._extract_auth_token(response.text)
            login_data = {'username': self.username, 'password': self.password}
            if token:
                login_data['token'] = token
            response = self.session.post(f"{self.base_url}{login_page}", data=login_data, timeout=10)
            if response.status_code == 200 and 'logout' in response.text.lower():
                self.is_authenticated = True
                logger.info("✅ Standard login successful")
                return True
        except:
            pass
        return False

    def _try_luci_login(self, login_page):
        try:
            logger.info("Trying LUCI login method...")
            login_data = {'username': self.username, 'password': self.password, 'operation': 'login'}
            response = self.session.post(f"{self.base_url}{login_page}", data=login_data, timeout=10)
            if response.status_code == 200 and 'logout' in response.text.lower():
                self.is_authenticated = True
                logger.info("✅ LUCI login successful")
                return True
        except:
            pass
        return False

    def _try_direct_login(self, login_page):
        try:
            logger.info("Trying direct login method...")
            login_data = {'username': self.username, 'password': self.password}
            response = self.session.post(f"{self.base_url}{login_page}", data=login_data, timeout=10)
            if response.status_code == 200 and 'logout' in response.text.lower():
                self.is_authenticated = True
                logger.info("✅ Direct login successful")
                return True
        except:
            pass
        return False

    def configure_wireless(self):
        if not self.is_authenticated:
            logger.error("❌ Not authenticated")
            return False
        wlan0_success = self._configure_wlan0()
        wlan5_success = self._configure_wlan5()
        return wlan0_success or wlan5_success

    def _configure_wlan0(self):
        wlan0_config = {
            'name': 'wlan0',
            'standard': 'n',
            'SSID': '{{SSID_2G}}',
            'regulatoryDomain': 'US',
            'autoChannelEnable': '1',
            'channel': '6',
            'X_TP_Bandwidth': 'Auto',
            'enable': '1',
            'SSIDAdvertisementEnabled': '1',
            'beaconType': '11i',
            'WPAEncryptionModes': 'AESEncryption',
            'IEEE11iEncryptionModes': 'AESEncryption',
            'WMMEnable': '1'
        }
        try:
            response = self.session.post(f"{self.base_url}/cgi-bin/luci/admin/network/wireless/wlan0", data=wlan0_config, timeout=10)
            if response.status_code == 200:
                logger.info("✅ wlan0 (2.4GHz) configured")
                return True
        except:
            pass
        return False

    def _configure_wlan5(self):
        wlan5_config = {
            'name': 'wlan5',
            'standard': 'ac',
            'SSID': '{{SSID_5G}}',
            'regulatoryDomain': 'US',
            'autoChannelEnable': '1',
            'channel': '36',
            'X_TP_Bandwidth': 'Auto',
            'enable': '0',
            'SSIDAdvertisementEnabled': '1',
            'beaconType': '11i',
            'WPAEncryptionModes': 'TKIPandAESEncryption',
            'IEEE11iEncryptionModes': 'AESEncryption',
            'WMMEnable': '1'
        }
        try:
            response = self.session.post(f"{self.base_url}/cgi-bin/luci/admin/network/wireless/wlan5", data=wlan5_config, timeout=10)
            if response.status_code == 200:
                logger.info("✅ wlan5 (5GHz) configured")
                return True
        except:
            pass
        return False

    def apply_changes(self):
        if not self.is_authenticated:
            logger.error("❌ Not authenticated")
            return False
        try:
            response = self.session.post(f"{self.base_url}/cgi-bin/luci/admin/network/wireless/apply", data={}, timeout=10)
            if response.status_code == 200:
                logger.info("✅ Changes applied")
                return True
        except:
            pass
        return False

def main():
    print("🔧 TP-Link Router Configuration Tool")
    print("=" * 50)

    router_ip = input("Router IP [default: {{ROUTER_IP}}]: ").strip() or "{{ROUTER_IP}}"
    username = input("Username [default: {{USERNAME}}]: ").strip() or "{{USERNAME}}"
    password = input("Password [default: {{PASSWORD}}]: ").strip() or "{{PASSWORD}}"

    router = TPLinkSimpleConfig(router_ip, username, password)

    if not router.test_connection():
        print("❌ Cannot connect to router.")
        return

    print("🔐 Logging in...")
    if not router.login():
        print("❌ Login failed.")
        return

    print("📡 Configuring wireless...")
    router.configure_wireless()

    print("💾 Applying changes...")
    if router.apply_changes():
        print("✅ Configuration complete.")
    else:
        print("⚠️ Could not apply configuration.")

if __name__ == "__main__":
    main()
