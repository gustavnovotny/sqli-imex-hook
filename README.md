SQLI Im/Ex for Liferay EE 6.1 GA2
=================================

Usage
-----

### Configuring import / export directory

The portal property `sqli.imex.deploy.dir` define the base directory for imports 
and exports. You can update this property in `portal-ext.properties` for example.

The default value is `${auto.deploy.deploy.dir}/imex`. Don't do any particular
update if this default value is right for you.

### Export

Put a file named `<some-name>.export.properties` in directory 
`${sqli.imex.deploy.dir}` : an export is automatically launched to a directory 
named `<some-name>`.
At the end of the export process, the `<some-name>.export.properties` file is 
renamed to `<some-name>.export.properties.done`.

### Import 

Put a file named `<some-name>.import.properties` in directory 
`${sqli.imex.deploy.dir}` and a directory named `<some-name>`, got back from a 
previous export: an import is automatically launched.

License
-------
This library, *SQLI Im/Ex for Liferay*, is free software ("Licensed
Software"); you can redistribute it and/or modify it under the terms of the [GNU
Lesser General Public License](http://www.gnu.org/licenses/lgpl-2.1.html) as
published by the Free Software Foundation; either version 2.1 of the License, or
(at your option) any later version.

This library is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; including but not limited to, the implied warranty of MERCHANTABILITY,
NONINFRINGEMENT, or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
Public License for more details.

You should have received a copy of the [GNU Lesser General Public
License](http://www.gnu.org/licenses/lgpl-2.1.html) along with this library; if
not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
Floor, Boston, MA 02110-1301 USA