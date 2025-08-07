#!/usr/bin/env python3
"""
TL-WR Router Configuration Fix

This script handles TL-WR series routers that return 403 errors
for standard endpoints but are accessible via the main page.
"""

import requests
import re
import logging

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

class TLWRFix:
    def __init__(self, router_ip="192.168.0.1", username="admin", password="admin"):
        self.router_ip = router_ip
        self.username = username
        self.password = password
        self.base_url = f"http://{router_ip}"
        self.session = requests.Session()
        
        # Headers that work better with TL-WR routers
        self.session.headers.update({
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36',
            'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
            'Accept-Language': 'en-US,en;q=0.5',
            'Accept-Encoding': 'gzip, deflate',
            'Connection': 'keep-alive',
            'Upgrade-Insecure-Requests': '1'
        })
        
    def test_connection(self):
        """Test basic connectivity"""
        try:
            response = self.session.get(self.base_url, timeout=10)
            if response.status_code == 200:
                logger.info("✅ Router accessible")
                return True
            else:
                logger.error(f"❌ Router status: {response.status_code}")
                return False
        except Exception as e:
            logger.error(f"❌ Connection failed: {e}")
            return False
    
    def find_login_method(self):
        """Find working login method for TL-WR"""
        logger.info("🔍 Finding TL-WR login method...")
        
        # Try different approaches
        methods = [
            self._try_main_page_login,
            self._try_direct_login,
            self._try_form_login
        ]
        
        for method in methods:
            if method():
                return True
        
        return False
    
    def _try_main_page_login(self):
        """Try login via main page"""
        try:
            # Get main page
            response = self.session.get(self.base_url, timeout=10)
            if response.status_code != 200:
                return False
            
            # Look for login form
            content = response.text
            if 'username' in content.lower() and 'password' in content.lower():
                logger.info("✅ Found login form on main page")
                
                # Extract any tokens
                token_match = re.search(r'name="token"\s+value="([^"]+)"', content)
                token = token_match.group(1) if token_match else None
                
                # Try to login
                login_data = {
                    'username': self.username,
                    'password': self.password
                }
                
                if token:
                    login_data['token'] = token
                
                response = self.session.post(self.base_url, data=login_data, timeout=10)
                
                if response.status_code == 200 and ('logout' in response.text.lower() or 'admin' in response.text.lower()):
                    logger.info("✅ Login successful via main page")
                    return True
            
            return False
            
        except Exception as e:
            logger.debug(f"Main page login failed: {e}")
            return False
    
    def _try_direct_login(self):
        """Try direct login endpoints"""
        try:
            endpoints = ['/login', '/admin', '/cgi-bin/luci']
            
            for endpoint in endpoints:
                try:
                    login_data = {
                        'username': self.username,
                        'password': self.password
                    }
                    
                    response = self.session.post(f"{self.base_url}{endpoint}", data=login_data, timeout=10)
                    
                    if response.status_code == 200 and ('logout' in response.text.lower() or 'admin' in response.text.lower()):
                        logger.info(f"✅ Login successful via {endpoint}")
                        return True
                        
                except Exception as e:
                    logger.debug(f"Direct login failed for {endpoint}: {e}")
                    continue
            
            return False
            
        except Exception as e:
            logger.debug(f"Direct login failed: {e}")
            return False
    
    def _try_form_login(self):
        """Try form-based login"""
        try:
            # Try to find and submit login form
            response = self.session.get(self.base_url, timeout=10)
            
            if response.status_code == 200:
                # Look for form action
                form_match = re.search(r'<form[^>]*action="([^"]*)"', response.text)
                if form_match:
                    form_action = form_match.group(1)
                    if not form_action.startswith('http'):
                        form_action = f"{self.base_url}{form_action}"
                    
                    login_data = {
                        'username': self.username,
                        'password': self.password
                    }
                    
                    response = self.session.post(form_action, data=login_data, timeout=10)
                    
                    if response.status_code == 200 and ('logout' in response.text.lower() or 'admin' in response.text.lower()):
                        logger.info("✅ Login successful via form")
                        return True
            
            return False
            
        except Exception as e:
            logger.debug(f"Form login failed: {e}")
            return False
    
    def configure_wireless(self):
        """Configure wireless settings"""
        logger.info("🔧 Configuring wireless settings...")
        
        # TL-WR configuration data
        config_data = {
            'ssid': 'Choose your own',
            'channel': '10',
            'mode': '11ng',
            'security': 'wpa2',
            'encryption': 'aes',
            'country': 'XX',
            'auto_channel': '1'
        }
        
        # Try different configuration endpoints
        endpoints = [
            '/cgi-bin/luci/admin/network/wireless',
            '/admin/network/wireless',
            '/cgi-bin/luci/admin/network/wireless/radio0'
        ]
        
        for endpoint in endpoints:
            try:
                response = self.session.post(f"{self.base_url}{endpoint}", data=config_data, timeout=10)
                if response.status_code == 200:
                    logger.info(f"✅ Configuration successful via {endpoint}")
                    return True
            except Exception as e:
                logger.debug(f"Configuration failed for {endpoint}: {e}")
                continue
        
        logger.warning("⚠️ Configuration may have failed")
        return False
    
    def apply_changes(self):
        """Apply configuration changes"""
        logger.info("🔄 Applying changes...")
        
        # Try different apply endpoints
        endpoints = [
            '/cgi-bin/luci/admin/network/wireless/apply',
            '/cgi-bin/luci/admin/network/apply',
            '/admin/network/wireless/apply'
        ]
        
        for endpoint in endpoints:
            try:
                response = self.session.post(f"{self.base_url}{endpoint}", data={}, timeout=10)
                if response.status_code == 200:
                    logger.info(f"✅ Changes applied via {endpoint}")
                    return True
            except Exception as e:
                logger.debug(f"Apply failed for {endpoint}: {e}")
                continue
        
        logger.warning("⚠️ Could not apply changes")
        return False

def main():
    print("🔧 TL-WR Router Configuration Fix")
    print("=" * 40)
    
    router_ip = input("Enter router IP (default: 192.168.0.1): ").strip() or "192.168.0.1"
    username = input("Enter username (default: admin): ").strip() or "admin"
    password = input("Enter password (default: admin): ").strip() or "admin"
    
    tlwr = TLWRFix(router_ip, username, password)
    
    # Test connection
    if not tlwr.test_connection():
        print("❌ Cannot connect to router")
        return
    
    # Find login method
    if not tlwr.find_login_method():
        print("❌ Cannot login to router")
        print("💡 Try accessing router manually in browser first")
        return
    
    # Configure wireless
    if tlwr.configure_wireless():
        print("✅ Wireless configuration successful")
    else:
        print("⚠️ Wireless configuration may have failed")
    
    # Apply changes
    if tlwr.apply_changes():
        print("✅ Configuration completed!")
    else:
        print("⚠️ Could not apply changes")

if __name__ == "__main__":
    main() 