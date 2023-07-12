import 'package:flutter/material.dart' hide Action, Page;
import 'package:flying_redux/flying_redux.dart';

import 'action.dart';
import 'state.dart';

buildEffect() {
  return combineEffects<$nameState>(<Object, Effect<$nameState>>{
    $nameAction.onSomeAction: _onSomeAction,
  });
}

void _onSomeAction(Action action, ComponentContext<$nameState> ctx) {
  if (action.payload == ctx.state.uniqueId) {
    Navigator.of(ctx.context)
        .pushNamed('some_route_name', arguments: ctx.state)
        .then((dynamic toDo) {
      if (toDo != null) {
        ctx.dispatch($nameActionCreator.someAction(toDo));
      }
    });
  }
}
