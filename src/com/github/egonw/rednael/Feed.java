/* Copyright (C) 2009  Egon Willighagen
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.egonw.rednael;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Feed {

    private String label;
    private URL url;
    private List<String> queue;

    public Feed(String label, URL url) {
        this.label = label;
        this.url = url;
        queue = new ArrayList<String>();
    }

    public String getLabel() {
        return this.label;
    }

    public URL getURL() {
        return this.url;
    }

    public boolean contains(String link) {
        return queue.contains(link);
    }

    public void add(String link) {
        queue.add(link);
    }

    public void reverse() {
        Collections.reverse(queue);
    }

}


