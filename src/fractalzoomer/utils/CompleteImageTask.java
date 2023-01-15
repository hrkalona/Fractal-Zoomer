/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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

import fractalzoomer.main.MainWindow;

import java.util.TimerTask;

/**
 *
 * @author kaloch
 */
public class CompleteImageTask extends TimerTask {
    private MainWindow ptr;
    private boolean d3;

    private boolean preview;
    private boolean zoomToCursor;
    
    public CompleteImageTask(MainWindow ptr, boolean d3, boolean preview, boolean zoomToCursor) {
        super();
        this.ptr = ptr;
        this.d3 = d3;
        this.preview = preview;
        this.zoomToCursor = zoomToCursor;
    }

    @Override
    public void run() {
        ptr.taskCompleteImage(d3, preview, zoomToCursor);
    }
    
}
