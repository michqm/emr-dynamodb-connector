/**
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
 * except in compliance with the License. A copy of the License is located at
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "LICENSE.TXT" file accompanying this file. This file is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under the License.
 */

package org.apache.hadoop.hive.dynamodb.type;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.hadoop.dynamodb.type.DynamoDBNumberSetType;
import org.apache.hadoop.hive.dynamodb.util.DynamoDBDataParser;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;

import java.util.List;

public class HiveDynamoDBNumberSetType extends DynamoDBNumberSetType implements HiveDynamoDBType {

  @Override
  public AttributeValue getDynamoDBData(Object data, ObjectInspector objectInspector) {
    List<String> values = DynamoDBDataParser.getSetAttribute(data, objectInspector, getDynamoDBType());
    return (values == null || values.isEmpty()) ? null : new AttributeValue().withNS(values);
  }

  @Override
  public TypeInfo getSupportedHiveType() {
    throw new UnsupportedOperationException(getClass().toString() + " does not support this operation.");
  }

  @Override
  public boolean supportsHiveType(TypeInfo typeInfo) {
    return typeInfo.equals(TypeInfoFactory.getListTypeInfo(TypeInfoFactory.doubleTypeInfo)) ||
            typeInfo.equals(TypeInfoFactory.getListTypeInfo(TypeInfoFactory.longTypeInfo));

  }

  @Override
  public Object getHiveData(AttributeValue data, ObjectInspector objectInspector) {
    return data.getNS() == null ? null : DynamoDBDataParser.getNumberObjectList(data.getNS(), objectInspector);
  }

}
