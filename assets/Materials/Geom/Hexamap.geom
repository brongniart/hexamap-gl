layout (points) in;
layout (line_strip) out;
layout (max_vertices = 11) out;

uniform mat4 g_WorldViewProjectionMatrix;
const float PI = 3.141592654;

const float SIZE = 2.0;

void main(){
	for (float i = 0.0; i <= 6; i++) {
		float offset_x = 3.0 / 2.0 * gl_in[0].gl_Position.x;
		float offset_y = sqrt(3) / 2.0 * gl_in[0].gl_Position.x + sqrt(3) * gl_in[0].gl_Position.y;
		
		float ang = 2.0 * PI  / 6 * i;
		
		vec2 corner = vec2((offset_x+cos(ang)) * SIZE, (offset_y+sin(ang)) * SIZE);
		gl_Position = g_WorldViewProjectionMatrix*vec4(corner.xy, 0.0, 1.0);
	
		EmitVertex();
	}
    EndPrimitive();
}

