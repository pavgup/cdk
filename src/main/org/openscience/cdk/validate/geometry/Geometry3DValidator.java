/* Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
 *                    2011  Egon Willighagen <egonw@users.sf.net>
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.validate.geometry;

import org.openscience.cdk.annotations.TestClass;
import org.openscience.cdk.annotations.TestMethod;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.validate.AbstractValidationTestType;
import org.openscience.cdk.validate.AbstractValidator;
import org.openscience.cdk.validate.ValidationReport;
import org.openscience.cdk.validate.ValidationTest;

/**
 * Validates the 3D geometry of the model.
 *
 * @cdk.module  valid
 * @cdk.githash
 *
 * @cdk.created 2006-05-11
 */ 
@TestClass("org.openscience.cdk.validate.geometry.Geometry3DValidatorTest")
public class Geometry3DValidator extends AbstractValidator {

    private final static AbstractValidationTestType TOO_LONG_BOND_LENGTH =
        new AbstractValidationTestType(
            "Bond length cannot exceed 3 Angstroms."
        ) {};

    public Geometry3DValidator() {}

    // assumes 1 unit in the coordinate system is one angstrom
    @TestMethod("testValidateBond")
    public ValidationReport validateBond(IBond subject) {
    	ValidationReport report = new ValidationReport();
    	// only consider two atom bonds
    	if (subject.getAtomCount() == 2) {
    		double distance = subject.getAtom(0).getPoint3d().distance(
    			subject.getAtom(2).getPoint3d()
    		);
    		if (distance > 3.0) { // should really depend on the elements
    			ValidationTest badBondLengthError = new ValidationTest(
    			    TOO_LONG_BOND_LENGTH, subject,
                    "A bond length typically is between 0.5 and 3.0 Angstroms."
                );
    			report.addError(badBondLengthError);
    		}
    	}
    	return report;
    }
}
