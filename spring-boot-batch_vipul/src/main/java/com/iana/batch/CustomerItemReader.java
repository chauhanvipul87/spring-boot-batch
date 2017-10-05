/*
 * MIT License
 *
 * Copyright (c) 2017 Alexey Saenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.iana.batch;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.IteratorItemReader;

/**
 * Customer item reader.
 *
 * @author Alexey Saenko (alexey.saenko@gmail.com)
 */
public class CustomerItemReader implements ItemReader<Customer> {

    private final String filename;

    private ItemReader<Customer> delegate;

    public CustomerItemReader(final String filename) {
        this.filename = filename;
    }

    @Override
    public Customer read() throws Exception {
        if (delegate == null) {
            System.out.println("Creating iterator item reader");
            delegate = new IteratorItemReader<>(customers());
        }
        System.out.println("Reading next customer");
        return delegate.read();
    }

    @SuppressWarnings("unchecked")
	private List<Customer> customers() throws FileNotFoundException {
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(filename))) {
            return (List<Customer>) decoder.readObject();
        }
    }
}
