package com.github.shradinx.ultraitems;

import java.io.Serial;
import java.io.Serializable;

public record SerializedCustomItem(String namespace, String id, byte[] item) implements Serializable {}
