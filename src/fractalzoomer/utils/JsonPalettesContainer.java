
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
            else if(entry.getKey().equals("values_double")) {
                ArrayList<ArrayList<Double>> values_double = (ArrayList<ArrayList<Double>>)entry.getValue();
                values = new ArrayList<>();
                for(ArrayList<Double> v : values_double) {
                    if(v.size() >= 3) {
                        ArrayList<Integer> rgb = new ArrayList<>();
                        rgb.add(ColorSpaceConverter.clamp((int)(v.get(0) * 255)));
                        rgb.add(ColorSpaceConverter.clamp((int)(v.get(1) * 255)));
                        rgb.add(ColorSpaceConverter.clamp((int)(v.get(2) * 255)));
                        values.add(rgb);
                    }
                }
            }
            else if(entry.getKey().equals("values_int")) {
                ArrayList<Integer> ints = (ArrayList<Integer>)entry.getValue();
                ArrayList<ArrayList<Integer>> vals = new ArrayList<>();

                for(Integer val : ints) {
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
