#!/usr/bin/env python3
"""
Advanced TP-Link Router Auto Configuration Script

This script automatically configures TP-Link WiFi router settings based on provided configuration data.
Supports multiple TP-Link router models and their specific APIs.
"""

import requests
import json
import time
import hashlib
import base64
import re
from urllib.parse import urljoin, urlparse
import logging
from typing import Dict, Any, Optional

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class TPLinkAdvancedConfig:
    def __init__(self, router_ip="192.168.0.1", username="admin", password="admin"):
        """
        Initialize TP-Link Router Configuration
        
        Args:
            router_ip (str): Router IP address (default: 192.168.0.1)
            username (str): Router admin username (default: admin)
            password (str): Router admin password (default: admin)
        """
        self.router_ip = router_ip
        self.username = username
        self.password = password
        self.base_url = f"http://{router_ip}"
        self.session = requests.Session()
        self.session.headers.update({
            'User-Agent': 'Mozilla/5.0',
            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json, text/plain, */*',
            'Accept-Language': 'en-US,en;q=0.9',
            'Accept-Encoding': 'gzip, deflate',
            'Connection': 'keep-alive',
            'Upgrade-Insecure-Requests': '1'
        })
        self.auth_token = None
        self.router_model = None
        
    def _detect_router_model(self):
        """Detect TP-Link router model"""
        try:
            endpoints = [
                "/",
                "/cgi-bin/luci",
                "/login",
                "/admin"
            ]
            
            for endpoint in endpoints:
                try:
                    response = self.session.get(f"{self.base_url}{endpoint}", timeout=5)
                    if response.status_code == 200:
                        content = response.text.lower()
                        
                        if 'archer' in content:
                            self.router_model = 'Archer'
                        elif 'tl-wr' in content:
                            self.router_model = 'TL-WR'
                        elif 'tl-mr' in content:
                            self.router_model = 'TL-MR'
                        else:
                            self.router_model = 'Generic'
                        
                        logger.info(f"Detected router model: {self.router_model}")
                        return True
                        
                except requests.exceptions.RequestException:
                    continue
            
            logger.warning("Could not detect router model, using generic approach")
            self.router_model = 'Generic'
            return True
            
        except Exception as e:
            logger.error(f"Error detecting router model: {e}")
            return False
    
    def _get_auth_token(self):
        """Get authentication token from router"""
        try:
            if self.router_model == 'Archer':
                return self._get_archer_auth_token()
            elif self.router_model in ['TL-WR', 'TL-MR']:
                return self._get_tl_auth_token()
            else:
                return self._get_generic_auth_token()
                
        except Exception as e:
            logger.error(f"Error getting auth token: {e}")
            return False
    
    def _get_archer_auth_token(self):
        """Get auth token for Archer series routers"""
        try:
            login_url = f"{self.base_url}/cgi-bin/luci"
            response = self.session.get(login_url, timeout=10)
            
            if response.status_code == 200:
                token_match = re.search(r'name="token"\s+value="([^"]+)"', response.text)
                if token_match:
                    self.auth_token = token_match.group(1)
                    logger.info("Extracted auth token for Archer router")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Archer auth token error: {e}")
            return False
    
    def _get_tl_auth_token(self):
        """Get auth token for TL-WR/TL-MR series routers"""
        try:
            login_url = f"{self.base_url}/login"
            response = self.session.get(login_url, timeout=10)
            
            if response.status_code == 200:
                token_match = re.search(r'name="token"\s+value="([^"]+)"', response.text)
                if token_match:
                    self.auth_token = token_match.group(1)
                    logger.info("Extracted auth token for TL router")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"TL auth token error: {e}")
            return False
    
    def _get_generic_auth_token(self):
        """Get auth token for generic routers"""
        try:
            login_url = f"{self.base_url}/cgi-bin/luci"
            response = self.session.get(login_url, timeout=10)
            
            if response.status_code == 200:
                logger.info("Successfully connected to router login page")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Generic auth token error: {e}")
            return False
    
    def _login(self):
        """Login to the router"""
        try:
            if self.router_model == 'Archer':
                return self._login_archer()
            elif self.router_model in ['TL-WR', 'TL-MR']:
                return self._login_tl()
            else:
                return self._login_generic()
                
        except Exception as e:
            logger.error(f"Login error: {e}")
            return False
    
    def _login_archer(self):
        """Login to Archer series router"""
        try:
            login_data = {
                'username': self.username,
                'password': self.password,
                'operation': 'login'
            }
            
            if self.auth_token:
                login_data['token'] = self.auth_token
            
            login_url = f"{self.base_url}/cgi-bin/luci"
            response = self.session.post(login_url, data=login_data, timeout=10)
            
            if response.status_code == 200:
                logger.info("Successfully logged in to Archer router")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Archer login error: {e}")
            return False
    
    def _login_tl(self):
        """Login to TL-WR/TL-MR series router"""
        try:
            login_data = {
                'username': self.username,
                'password': self.password,
                'operation': 'login'
            }
            
            if self.auth_token:
                login_data['token'] = self.auth_token
            
            login_url = f"{self.base_url}/login"
            response = self.session.post(login_url, data=login_data, timeout=10)
            
            if response.status_code == 200:
                logger.info("Successfully logged in to TL router")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"TL login error: {e}")
            return False
    
    def _login_generic(self):
        """Login to generic router"""
        try:
            login_data = {
                'username': self.username,
                'password': self.password,
                'operation': 'login'
            }
            
            login_url = f"{self.base_url}/cgi-bin/luci"
            response = self.session.post(login_url, data=login_data, timeout=10)
            
            if response.status_code == 200:
                logger.info("Successfully logged in to router")
                return True
            return False
            
        except requests.exceptions.RequestException as e:
            logger.error(f"Generic login error: {e}")
            return False
    
    def _send_config_request(self, endpoint: str, data: Dict[str, Any]) -> Optional[Any]:
        """Send configuration request to router"""
        try:
            url = urljoin(self.base_url, endpoint)
            
            if self.auth_token:
                data['token'] = self.auth_token
            
            response = self.session.post(url, data=data, timeout=10)
            
            if response.status_code == 200:
                logger.info(f"Configuration request successful: {endpoint}")
                return response.json() if response.headers.get('content-type', '').startswith('application/json') else response.text
            else:
                logger.error(f"Configuration request failed: {response.status_code}")
                return None
                
        except requests.exceptions.RequestException as e:
            logger.error(f"Configuration request error: {e}")
            return None
    
    def configure_wlan0(self):
        """Configure wlan0 (2.4GHz) settings"""
        wlan0_config = {
    'name': 'wlan0',
    'standard': 'n',
    'SSID': 'Choose your own',
    'regulatoryDomain': 'DE',
    'autoChannelEnable': '1',
    'channel': '10',
    'X_TP_Bandwidth': 'Auto',
    'enable': '1',
    'SSIDAdvertisementEnabled': '1',
    'beaconType': '11i',  # WPA2
    'basicEncryptionModes': 'None',
    'WPAEncryptionModes': 'AESEncryption',
    'IEEE11iEncryptionModes': 'AESEncryption',
    'keyPassphrase': 'YourPasswordHere',  # <-- Add your SSID password here
    'X_TP_Configuration_Modified': '0',
    'WMMEnable': '1',
    'X_TP_FragmentThreshold': '2346'
}

        
        logger.info("Configuring wlan0 (2.4GHz) settings...")
        
        endpoints = [
            "/cgi-bin/luci/admin/network/wireless/wlan0",
            "/cgi-bin/luci/admin/network/wireless",
            "/admin/network/wireless/wlan0"
        ]
        
        for endpoint in endpoints:
            result = self._send_config_request(endpoint, wlan0_config)
            if result is not None:
                return result
        
        return None
    
    def configure_wlan5(self):
        """Configure wlan5 (5GHz) settings"""
        wlan5_config = {
            'name': 'wlan5',
            'standard': 'ac',
            'SSID': 'TP-',
            'regulatoryDomain': 'DE',
            'autoChannelEnable': '1',
            'channel': '40',
            'X_TP_Bandwidth': 'Auto',
            'enable': '0',
            'SSIDAdvertisementEnabled': '1',
            'beaconType': '11i',
            'basicEncryptionModes': 'None',
            'WPAEncryptionModes': 'TKIPandAESEncryption',
            'IEEE11iEncryptionModes': 'AESEncryption',
            'X_TP_Configuration_Modified': '0',
            'WMMEnable': '1',
            'X_TP_FragmentThreshold': '2346'
        }
        
        logger.info("Configuring wlan5 (5GHz) settings...")
        
        endpoints = [
            "/cgi-bin/luci/admin/network/wireless/wlan5",
            "/cgi-bin/luci/admin/network/wireless",
            "/admin/network/wireless/wlan5"
        ]
        
        for endpoint in endpoints:
            result = self._send_config_request(endpoint, wlan5_config)
            if result is not None:
                return result
        
        return None
    
    def apply_configuration(self):
        """Apply all configurations"""
        logger.info("Starting TP-Link router configuration...")
        
        if not self._detect_router_model():
            logger.error("Failed to detect router model")
            return False
        
        if not self._get_auth_token():
            logger.error("Failed to get authentication token")
            return False
        
        if not self._login():
            logger.error("Failed to login to router")
            return False
        
        wlan0_result = self.configure_wlan0()
        if wlan0_result is None:
            logger.error("Failed to configure wlan0")
            return False
        
        wlan5_result = self.configure_wlan5()
        if wlan5_result is None:
            logger.error("Failed to configure wlan5")
            return False
        
        logger.info("Applying configuration changes...")
        apply_endpoints = [
            "/cgi-bin/luci/admin/network/wireless/apply",
            "/cgi-bin/luci/admin/network/apply",
            "/admin/network/wireless/apply"
        ]
        
        for endpoint in apply_endpoints:
            apply_result = self._send_config_request(endpoint, {})
            if apply_result is not None:
                logger.info("Configuration applied successfully!")
                return True
        
        logger.error("Failed to apply configuration")
        return False
    
    def test_connection(self):
        """Test connection to router"""
        try:
            response = self.session.get(f"{self.base_url}/", timeout=5)
            if response.status_code == 200:
                logger.info("Router is accessible")
                return True
            else:
                logger.warning(f"Router responded with status code: {response.status_code}")
                return False
        except requests.exceptions.RequestException as e:
            logger.error(f"Cannot connect to router: {e}")
            return False

def main():
    """Main function"""
    print("Advanced TP-Link Router Auto Configuration Tool")
    print("=" * 50)
    
    router_ip = input("Enter router IP address (default: 192.168.0.1): ").strip() or "192.168.0.1"
    username = input("Enter router username (default: admin): ").strip() or "admin"
    password = input("Enter router password (default: admin): ").strip() or "admin"
    
    router = TPLinkAdvancedConfig(router_ip, username, password)
    
    print(f"\nTesting connection to {router_ip}...")
    if not router.test_connection():
        print("Cannot connect to router. Please check:")
        print("1. Router IP address is correct")
        print("2. Router is powered on and connected")
        print("3. You are connected to the router's network")
        return
    
    print("\nApplying router configuration...")
    if router.apply_configuration():
        print("\n✅ Configuration completed successfully!")
        print("\nConfiguration Summary:")
        print("- wlan0 (2.4GHz): SSID 'TPLINK', Channel 10, WPA2-AES")
        print("- wlan5 (5GHz): SSID 'TP-', Channel 40, Disabled")
        print("- Regulatory Domain: DE")
        print("- Auto Channel: Enabled")
        print(f"- Router Model: {router.router_model}")
    else:
        print("\n❌ Configuration failed. Please check the logs above for details.")

if __name__ == "__main__":
    main()
