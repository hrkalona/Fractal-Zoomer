/*
 * Copyright (C) 2020 hrkalona
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

public class JsonPalettesContainer {
    private ArrayList<Object> palettes;

    public JsonPalettesContainer() {
        InputStream input = getClass().getResourceAsStream("/fractalzoomer/palettes/palettes.json");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readerForUpdating(this).readValue(input);
        }
        catch (Exception ex) {

        }
        Collections.sort(palettes, (o1, o2) -> {
            Set<Map.Entry<String,Object>> set1 = ((LinkedHashMap<String,Object>)o1).entrySet();
            Set<Map.Entry<String,Object>> set2 = ((LinkedHashMap<String,Object>)o2).entrySet();

            String name1 = "";
            for(Map.Entry<String,Object> entry : set1) {
                if(entry.getKey().equals("name")) {
                    name1 = ((String)entry.getValue()).toLowerCase();
                }
            }

            String name2 = "";
            for(Map.Entry<String,Object> entry : set2) {
                if(entry.getKey().equals("name")) {
                    name2 = ((String)entry.getValue()).toLowerCase();
                }
            }

            return name1.compareTo(name2);
        });
    }

    public ArrayList<Object> getPalettes() {
        return palettes;
    }

    public String[] getNames() {

        ArrayList<String> names = new ArrayList<>();

        for(Object set : palettes) {
            Set<Map.Entry<String,Object>> a = ((LinkedHashMap<String,Object>)set).entrySet();

            for(Map.Entry<String,Object> entry : a) {
                if(entry.getKey().equals("name")) {
                    names.add(((String)entry.getValue()).toLowerCase());
                }
            }
        }

        return names.toArray(new String[names.size()]);
    }

    public ArrayList<ArrayList<Integer>> getPalette(int i, int maxColors) {

        if(palettes == null || i >= palettes.size()) {
            return new ArrayList<>();
        }

        Set<Map.Entry<String,Object>> a = ((LinkedHashMap<String,Object>)palettes.get(i)).entrySet();

        ArrayList<ArrayList<Integer>> values = null;

        for(Map.Entry<String,Object> entry : a) {
            if(entry.getKey().equals("values")) {
                values = (ArrayList<ArrayList<Integer>>)entry.getValue();
            }
            else if(entry.getKey().equals("values_hex")) {
                ArrayList<String> hexs = (ArrayList<String>)entry.getValue();
                ArrayList<ArrayList<Integer>> vals = new ArrayList<>();

                for(String hex : hexs) {
                    int val = (int)((long)Long.decode(hex));
                    ArrayList<Integer> rgb = new ArrayList<>();
                    rgb.add((val >> 16) & 0xFF);
                    rgb.add((val >> 8) & 0xFF);
                    rgb.add(val & 0xFF);

                    vals.add(rgb);
                }

                values = vals;
            }
            else if(entry.getKey().equals("values_int")) {
                ArrayList<String> ints = (ArrayList<String>)entry.getValue();
                ArrayList<ArrayList<Integer>> vals = new ArrayList<>();

                for(String v : ints) {
                    int val = Integer.parseInt(v);
                    ArrayList<Integer> rgb = new ArrayList<>();
                    rgb.add((val >> 16) & 0xFF);
                    rgb.add((val >> 8) & 0xFF);
                    rgb.add(val & 0xFF);

                    vals.add(rgb);
                }

                values = vals;
            }
        }

        if(values == null) {
            return new ArrayList<>();
        }

        if(values.size() <= maxColors) {
            return values;
        }

        ArrayList<ArrayList<Integer>> subset = new ArrayList<>();

        double div = values.size() / ((double)maxColors);

        for(int k = 0; k < maxColors; k++) {
            subset.add(values.get((int)(k * div + 0.5)));
        }

        return subset;

    }

}
