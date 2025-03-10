package net.createmod.catnip.render;

import java.nio.ByteBuffer;

import com.mojang.blaze3d.vertex.BufferBuilder;

import com.mojang.blaze3d.vertex.MeshData;

import net.caffeinemc.mods.sodium.api.util.NormI8;
import net.irisshaders.iris.vertices.NormalHelper;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MutableTemplateMesh extends TemplateMesh {
	public MutableTemplateMesh(int[] data) {
		super(data);
	}

	public MutableTemplateMesh(int vertexCount) {
		super(vertexCount);
	}

	public void copyFrom(int index, TemplateMesh template) {
		System.arraycopy(template.data, 0, data, index * INT_STRIDE, template.data.length);
	}

	public MutableTemplateMesh(MeshData data) {
		this(data.drawState().vertexCount());
		int vertexCount = data.drawState().vertexCount();
		ByteBuffer vertexBuffer = data.vertexBuffer();
		int stride = data.drawState().format().getVertexSize();

		transferFromVertexData(0, 0, vertexCount, this, vertexBuffer, stride);
	}

	public static void transferFromVertexData(int srcIndex, int dstIndex, int vertexCount, MutableTemplateMesh mutableMesh, ByteBuffer vertexBuffer, int stride) {
		for (int i = 0; i < vertexCount; i += 4) {
			int reader = i * stride;
			int reader1 = reader + stride;
			int reader2 = reader1 + stride;
			int reader3 = reader2 + stride;

			int normal = vertexBuffer.getInt(srcIndex + reader + 28);
			float x0 = vertexBuffer.getFloat(srcIndex + reader);
			float y0 = vertexBuffer.getFloat(srcIndex + reader + 4);
			float z0 = vertexBuffer.getFloat(srcIndex + reader + 8);
			float x1 = vertexBuffer.getFloat(srcIndex + reader1);
			float y1 = vertexBuffer.getFloat(srcIndex + reader1 + 4);
			float z1 = vertexBuffer.getFloat(srcIndex + reader1 + 8);
			float x2 = vertexBuffer.getFloat(srcIndex + reader2);
			float y2 = vertexBuffer.getFloat(srcIndex + reader2 + 4);
			float z2 = vertexBuffer.getFloat(srcIndex + reader2 + 8);
			float u0 = vertexBuffer.getFloat(srcIndex + reader + 16);
			float v0 = vertexBuffer.getFloat(srcIndex + reader + 20);
			float u1 = vertexBuffer.getFloat(srcIndex + reader1 + 16);
			float v1 = vertexBuffer.getFloat(srcIndex + reader1 + 20);
			float u2 = vertexBuffer.getFloat(srcIndex + reader2 + 16);
			float v2 = vertexBuffer.getFloat(srcIndex + reader2 + 20);

			int tangent = NormalHelper.computeTangent(null,
				NormI8.unpackX(normal), NormI8.unpackY(normal), NormI8.unpackZ(normal),
				x0, y0, z0, u0, v0,
				x1, y1, z1, u1, v1,
				x2, y2, z2, u2, v2);

			mutableMesh.x(dstIndex + i, x0);
			mutableMesh.y(dstIndex + i, y0);
			mutableMesh.z(dstIndex + i, z0);
			mutableMesh.color(dstIndex + i, vertexBuffer.getInt(srcIndex + reader + 12));
			mutableMesh.u(dstIndex + i, u0);
			mutableMesh.v(dstIndex + i, v0);
			mutableMesh.overlay(dstIndex + i, OverlayTexture.NO_OVERLAY);
			mutableMesh.light(dstIndex + i, vertexBuffer.getInt(srcIndex + reader + 24));
			mutableMesh.normal(dstIndex + i, normal);
			mutableMesh.tangent(dstIndex + i, tangent);

			mutableMesh.x(dstIndex + i + 1, x1);
			mutableMesh.y(dstIndex + i + 1, y1);
			mutableMesh.z(dstIndex + i + 1, z1);
			mutableMesh.color(dstIndex + i + 1, vertexBuffer.getInt(srcIndex + reader1 + 12));
			mutableMesh.u(dstIndex + i + 1, u1);
			mutableMesh.v(dstIndex + i + 1, v1);
			mutableMesh.overlay(dstIndex + i + 1, OverlayTexture.NO_OVERLAY);
			mutableMesh.light(dstIndex + i + 1, vertexBuffer.getInt(srcIndex + reader1 + 24));
			mutableMesh.normal(dstIndex + i + 1, normal);
			mutableMesh.tangent(dstIndex + i + 1, tangent);

			mutableMesh.x(dstIndex + i + 2, x2);
			mutableMesh.y(dstIndex + i + 2, y2);
			mutableMesh.z(dstIndex + i + 2, z2);
			mutableMesh.color(dstIndex + i + 2, vertexBuffer.getInt(srcIndex + reader2 + 12));
			mutableMesh.u(dstIndex + i + 2, u2);
			mutableMesh.v(dstIndex + i + 2, v2);
			mutableMesh.overlay(dstIndex + i + 2, OverlayTexture.NO_OVERLAY);
			mutableMesh.light(dstIndex + i + 2, vertexBuffer.getInt(srcIndex + reader2 + 24));
			mutableMesh.normal(dstIndex + i + 2, normal);
			mutableMesh.tangent(dstIndex + i + 2, tangent);

			mutableMesh.x(dstIndex + i + 3, vertexBuffer.getFloat(srcIndex + reader3));
			mutableMesh.y(dstIndex + i + 3, vertexBuffer.getFloat(srcIndex + reader3 + 4));
			mutableMesh.z(dstIndex + i + 3, vertexBuffer.getFloat(srcIndex + reader3 + 8));
			mutableMesh.color(dstIndex + i + 3, vertexBuffer.getInt(srcIndex + reader3 + 12));
			mutableMesh.u(dstIndex + i + 3, vertexBuffer.getFloat(srcIndex + reader3 + 16));
			mutableMesh.v(dstIndex + i + 3, vertexBuffer.getFloat(srcIndex + reader3 + 20));
			mutableMesh.overlay(dstIndex + i + 3, OverlayTexture.NO_OVERLAY);
			mutableMesh.light(dstIndex + i + 3, vertexBuffer.getInt(srcIndex + reader3 + 24));
			mutableMesh.normal(dstIndex + i + 3, normal);
			mutableMesh.tangent(dstIndex + i + 3, tangent);
		}
	}

	public void x(int index, float x) {
		data[index * INT_STRIDE + X_OFFSET] = Float.floatToRawIntBits(x);
	}

	public void y(int index, float y) {
		data[index * INT_STRIDE + Y_OFFSET] = Float.floatToRawIntBits(y);
	}

	public void z(int index, float z) {
		data[index * INT_STRIDE + Z_OFFSET] = Float.floatToRawIntBits(z);
	}

	public void color(int index, int color) {
		data[index * INT_STRIDE + COLOR_OFFSET] = color;
	}

	public void u(int index, float u) {
		data[index * INT_STRIDE + U_OFFSET] = Float.floatToRawIntBits(u);
	}

	public void v(int index, float v) {
		data[index * INT_STRIDE + V_OFFSET] = Float.floatToRawIntBits(v);
	}

	public void overlay(int index, int overlay) {
		data[index * INT_STRIDE + OVERLAY_OFFSET] = overlay;
	}

	public void light(int index, int light) {
		data[index * INT_STRIDE + LIGHT_OFFSET] = light;
	}

	public void normal(int index, int normal) {
		data[index * INT_STRIDE + NORMAL_OFFSET] = normal;
	}

	public void tangent(int index, int tangent) {
		data[index * INT_STRIDE + TANGENT_OFFSET] = tangent;
	}

	public TemplateMesh toImmutable() {
		return new TemplateMesh(data);
	}
}
