layout (points) in;
layout (line_strip) out;
layout (max_vertices = 7) out;

uniform mat4 g_WorldViewProjectionMatrix;
const float PI = 3.141592654;

uniform int m_Size;
uniform bool m_Pointy;

void main(){
	for (float i = 0.0; i <= 6.0; i++) {
		
		float offset_x = 0;
		float offset_y = 0;
		float offset_angle = 0;
		
		if (m_Pointy) {
			offset_x = sqrt(3) * gl_in[0].gl_Position.x + sqrt(3.0)/2.0 * gl_in[0].gl_Position.y;
			offset_y = 3.0 / 2.0 * gl_in[0].gl_Position.y; 
			offset_angle = 0.5;
		} else {
			offset_x = 3.0 / 2.0 * gl_in[0].gl_Position.x;
			offset_y = sqrt(3) / 2.0 * gl_in[0].gl_Position.x + sqrt(3) * gl_in[0].gl_Position.y;
		}
		
		float ang = 2.0 * PI  * (offset_angle+i)/ 6.0 ;
		
		vec2 corner = vec2((offset_x+cos(ang)) * m_Size, (offset_y+sin(ang)) * m_Size);
		gl_Position = g_WorldViewProjectionMatrix*vec4(corner.xy, 0.0, 1.0);
	
		EmitVertex();
	}
    EndPrimitive();
}

