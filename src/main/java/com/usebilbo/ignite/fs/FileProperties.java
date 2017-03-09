package com.usebilbo.ignite.fs;

import java.util.EnumSet;
import java.util.Set;

public interface FileProperties {
    public static final String USER_NAME = "usrName";
    public static final String GROUP_NAME = "grpName";
    public static final String PERMISSION = "permission";
    
    public static final Rights[] RIGHTS = Rights.values();
    
    public static enum Rights {
        R('r'),
        W('w'),
        X('x')
        ;
        
        private final char label;
        
        private Rights(char label) {
            this.label = label;
        }
        
        public char label() {
            return label;
        }
    }
    
    public static enum Parts {
        USER(0, 2),
        GROUP(3, 5),
        OTHER(6, 8)
        ;
        private int start;
        private int stop;

        private Parts(int start, int stop) {
            this.start = start;
            this.stop = stop;
        }
        
        public Set<Rights> parse(String source) {
            Set<Rights> result = EnumSet.noneOf(Rights.class);
            
            if (source == null || source.length() < stop) {
                return result; 
            }
            
            for (int i = 0; i < RIGHTS.length; i++) {
                if (source.charAt(start + i) == RIGHTS[i].label) {
                    result.add(RIGHTS[i]);
                }
            }
            
            return result;
        }
    }
}
