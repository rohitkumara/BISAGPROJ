UPDATE texas_roads_gcs
   SET  x_min=ST_Xmin(geom), y_min=ST_Ymin(geom), 
       x_max=ST_Xmax(geom), y_max=ST_Ymax(geom)
 WHERE geom IS NOT NULL;
