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
import java.util.LinkedList;

public class Feed {

    private String label;
    private URL url;
    private LinkedList<String> queue;

    public Feed(String label, URL url) {
        this.label = label;
        this.url = url;
        queue = new LinkedList<String>();
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
        System.out.println("Added link: " + link);
        queue.addFirst(link);
        // feeds that are smaller than what they normally should be, e.g.
        // because they just started, will mess up... assume feeds have 50
        // items, then the when only 8 out of 10 are given (== partial feed)
        // , will not mess up the same. Fails for partial feeds with more than
        // 50 items as default, but that should be rare (tm)
        if (queue.size() > 50)
            queue.removeLast();
    }

    public void addInitial(String link) {
        System.out.println("Added initial link: " + link);
        // the first time a feed is read, the first item must be at the top
        // of the list, so next is added always as last
        queue.addLast(link);
    }

}


