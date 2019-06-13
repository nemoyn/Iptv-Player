/*
 * Copyright (C) 2019 Nataniel
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */
package iptv;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * @author Nataniel
 */
public class Propriedades {

    public static Propriedades instancia;
    private PropertiesConfiguration propertiesConfiguration;
    private Logger logger;
    private final String arquivo = "data.properties";

    Propriedades() {
        instancia = this;
        logger = Logger.getLogger(getClass());
        try {
            File f = new File(arquivo);
            if (!f.exists()) f.createNewFile();
            propertiesConfiguration = new PropertiesConfiguration(f);
        } catch (Exception e) {
            iptv.IPTVPlayer.error(e, getClass());
        }
    }

    public String[] getM3u() {
        return propertiesConfiguration.getStringArray("m3u");
    }

    public void setM3u(String[] m3u) {
        propertiesConfiguration.setProperty("m3u", m3u);
        try {
            propertiesConfiguration.save();
            logger.debug("Inserido " + m3u.length + " URLs na chave m3u.");
        } catch (ConfigurationException e) {
            IPTVPlayer.error(e, getClass());
        }
    }

    public void addConfigurationListener(ConfigurationListener listener) {
        propertiesConfiguration.addConfigurationListener(listener);
    }

    public boolean isDebug() {
        String deb = propertiesConfiguration.getString("debug", "");
        boolean ret;
        if (deb.isEmpty()) {
            ret = false;
            propertiesConfiguration.setProperty("debug", false);
            try {
                propertiesConfiguration.save();
            } catch (ConfigurationException e) {
                IPTVPlayer.error(e, getClass());
            }
        } else {
            ret = Boolean.valueOf(deb);
        }
        logger.info("Debug esta " + (ret ? "Ativado" : "Desativado"));
        return ret;
    }

    public String getM3uLocal() {
        return propertiesConfiguration.getString("m3uLocal");
    }

    public int getLocalTime() {
        int a = propertiesConfiguration.getInt("localTime", -1);
        if (a == -1) {
            a = 5000;
            propertiesConfiguration.setProperty("localTime", a);
            try {
                propertiesConfiguration.save();
            } catch (ConfigurationException e) {
                IPTVPlayer.error(e, getClass());
            }
        }
        return a;

    }

    public int getLinkTime() {
        int a = propertiesConfiguration.getInt("linkTime", -1);
        if (a == -1) {
            a = 10000;
            propertiesConfiguration.setProperty("linkTime", a);
            try {
                propertiesConfiguration.save();
            } catch (ConfigurationException e) {
                IPTVPlayer.error(e, getClass());
            }
        }
        return a;

    }

    public void setM3uLocal(String m3uLocal) {
        propertiesConfiguration.setProperty("m3uLocal", m3uLocal);
        try {
            propertiesConfiguration.save();
        } catch (ConfigurationException e) {
            IPTVPlayer.error(e, getClass());
        }
    }
}
